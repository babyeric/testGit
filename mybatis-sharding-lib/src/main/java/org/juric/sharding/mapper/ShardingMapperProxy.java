package org.juric.sharding.mapper;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.juric.sharding.annotation.LogicalDbName;
import org.juric.sharding.annotation.ShardMethod;
import org.juric.sharding.strategy.ShardingStrategy;
import org.juric.sharding.strategy.StrategyRegistry;
import org.juric.sharding.strategy.StrategyResult;
import org.springframework.core.annotation.AnnotationUtils;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/19/15
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShardingMapperProxy<T> implements InvocationHandler, Serializable {
    private SqlSession sqlSession;
    private T imp;
    private Class<T> mapperInterface;
    private final static String PARAM_NOW = "_now_";
    private final static String PARAM_LOGICAL_SHARD_ID = "_logicalShardId_";

    private interface Function<In, Out> {
        Out execute(In input);
    }

    public ShardingMapperProxy(Class<T> mapper, SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        this.imp = sqlSession.getMapper(mapper);
        this.mapperInterface = mapper;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ShardingMapperContext context = ShardingMapperContext.newMapperContext();
        String logicalDbName = findLogicalDBName(mapperInterface);
        ShardMethod shardMethod = method.getAnnotation(ShardMethod.class);
        ShardingStrategy shardingStrategy = StrategyRegistry.resolve(shardMethod.value());
        StrategyResult strategyResult = shardingStrategy.resolve(logicalDbName, method, args);

        SqlCommandType sqlCommandType =  resolveSqlCommandType(mapperInterface, method, sqlSession.getConfiguration());
        beforeMethodInvoke(sqlCommandType, strategyResult, method, args);

        Map<String, Object> extraParam = buildExtraParams(strategyResult, sqlCommandType == SqlCommandType.INSERT);

        int[] shardIds = strategyResult.getPhysicalShardIds();
        if (shardIds == null) {
            int[] logicalShardIds = strategyResult.getLogicalShardIds();
            shardIds = new int[logicalShardIds.length];
            for(int i=0; i<shardIds.length; ++i) {
                shardIds[i] = ShardingMapperUtils.logicalToPhysicalId(logicalDbName, logicalShardIds[i]);
            }
        }


        final ShardingMapperMethod mapperMethod = new ShardingMapperMethod(mapperInterface, method, sqlSession.getConfiguration(), extraParam);
        Function<Integer, Object> processor = createProcessor(mapperMethod, logicalDbName, args);

        List<Object> results;
        try
        {
            results = processInSequence(processor, shardIds);
        } finally {
            ShardingMapperContext.clearMapperContext();
        }

        if (shardIds.length == 1) {
            return results.get(0);
        } else {
            return merge(results, method.getReturnType(), sqlCommandType);
        }
    }

    private SqlCommandType resolveSqlCommandType(Class<?> mapperInterface, Method method, Configuration configuration) {
        String sqlStatementName =  mapperInterface.getName() + "." + method.getName();
        return configuration.getMappedStatement(sqlStatementName).getSqlCommandType();
    }

    private String findLogicalDBName(Class<T> mapperInterface) {
        LogicalDbName abcDBName = AnnotationUtils.findAnnotation(mapperInterface, LogicalDbName.class);
        return abcDBName.value();
    }

    private Map<String, Object> buildExtraParams(StrategyResult strategyResult, boolean isInsert) {
        Map<String, Object> extraParam = new HashMap<>();
        extraParam.put(PARAM_NOW, new Date());
        if(isInsert) {
            if (strategyResult.getLogicalShardIds().length != 1) {
                throw new IllegalArgumentException();
            }
            extraParam.put(PARAM_LOGICAL_SHARD_ID, strategyResult.getLogicalShardIds()[0]);
        }
        return extraParam;
    }

    private Function<Integer, Object> createProcessor(final ShardingMapperMethod mapperMethod, final String logicalDbName, final Object[] args) {
        return new Function<Integer, Object>() {
            @Override
            public Object execute(Integer input) {
                int shardId = input;
                DataSource dataSource = ShardingMapperUtils.getDataSource(logicalDbName, shardId);
                ShardingMapperContext.getMapperContext().setDataSource(dataSource);

                return mapperMethod.execute(sqlSession, args);
            }
        };
    }

    private List<Object> processInSequence(Function<Integer, Object> processor, int[] shardIds) {
        List<Object> results = new ArrayList<Object>();
        for (int shardId : shardIds) {
            results.add(processor.execute(shardId));
        }

        return results;
    }

    private static <T> T merge(List<T> results, Class<?> returnType, SqlCommandType commandType) {
        boolean returnsMany = Collection.class.isAssignableFrom(returnType) || returnType.isArray();

        T mergedResult = null;
        if (List.class.isAssignableFrom(returnType)) {
            mergedResult = (T) new ArrayList<>();
            for (T result : results) {
                ((List<Object>) mergedResult).addAll((Collection<Object>) result);
            }
        } else if (Map.class.isAssignableFrom(returnType)) {
            mergedResult = (T) new HashMap<>();
            for (T result : results) {
                ((Map<Object, Object>) mergedResult).putAll((Map<Object, Object>) result);
            }
        } else if (Set.class.isAssignableFrom(returnType)) {
            mergedResult = (T) new HashSet<>();
            for (T result : results) {
                ((Set<Object>) mergedResult).addAll((Collection<Object>) result);
            }
        } else if (returnType.isArray()) {
            int totalLen = 0;
            for (T result : results) {
                totalLen += Array.getLength(result);
            }

            Object target = Array.newInstance(returnType.getComponentType(), totalLen);
            int index = 0;
            for (T result : results) {
                int len = Array.getLength(result);
                System.arraycopy(result, 0, target, index, len);
                index += len;
            }

            mergedResult = (T) target;
        } else if (!returnsMany) {
            Integer affectedRows = 0;

            for (T result : results) {
                if (result == null)
                    continue;

                if (commandType == SqlCommandType.SELECT) {
                    if (mergedResult != null)
                        throw new TooManyResultsException("The result should be unique for SELECT across all shards.");
                } else {
                    // sum affected rows for non-select statement
                    affectedRows += (Integer) result;
                }

                mergedResult = result;
            }

            if (commandType != SqlCommandType.SELECT) {
                mergedResult = (T) affectedRows;
            }
        } else {
            throw new IllegalArgumentException("Currently don't support merge result for return type [" + returnType.getCanonicalName() + "]");
        }

        return mergedResult;
    }

    protected void beforeMethodInvoke(SqlCommandType sqlCommandType, StrategyResult strategyResult, Method method, Object[] args) {

    }
}
