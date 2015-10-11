package com.practice.def;

import com.practice.def.annotation.ShardGeneratedId;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;
import org.juric.sharding.annotation.ShardParam;
import org.juric.sharding.mapper.ShardingMapperProxy;
import org.juric.sharding.strategy.StrategyResult;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Eric on 9/13/2015.
 */
public class DefMapperProxy<T> extends ShardingMapperProxy {
    private final static Log LOG = LogFactory.getLog(DefMapperProxy.class);
    private DefShardIdGenerator defShardIdGenerator;

    void setDefShardIdGenerator(DefShardIdGenerator defShardIdGenerator) {
        this.defShardIdGenerator = defShardIdGenerator;
    }

    public DefMapperProxy(Class mapper, SqlSession sqlSession) {
        super(mapper, sqlSession);
    }

    protected void beforeMethodInvoke(SqlCommandType sqlCommandType, StrategyResult strategyResult, Method method, Object[] args) {
        super.beforeMethodInvoke(sqlCommandType, strategyResult, method, args);
        if (sqlCommandType != SqlCommandType.INSERT) {
            return;
        }

        if (strategyResult.getLogicalShardIds() == null) {
            return;
        }

        if (strategyResult.getLogicalShardIds().length != 1) {
            throw new IllegalArgumentException("only support one shard operation");
        }

        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i=0; i<annotations.length; ++i) {
            for (int j=0; j<annotations[i].length; ++j) {
                if (annotations[i][j] instanceof ShardParam) {
                    generateId(strategyResult.getLogicalShardIds()[0], args[i]);
                }
            }
        }
    }

    protected void generateId(int logicalSharidId, Object arg) {
        Class<?> cls = arg.getClass();
        if (cls.isPrimitive()) {
            return;
        }

        try {
            BeanInfo info = Introspector.getBeanInfo(cls);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor prop : props) {
                Method setter = prop.getWriteMethod();
                Method getter = prop.getReadMethod();
                if (setter == null || getter == null) {
                    continue;
                }

                ShardGeneratedId shardGeneratedId = setter.getAnnotation(ShardGeneratedId.class);
                if (shardGeneratedId != null) {
                    validateSetterAndGetter(setter, getter);
                    Long id = (Long)getter.invoke(arg);
                    if (id == null) {
                        id = defShardIdGenerator.generate(shardGeneratedId.value(), logicalSharidId);
                        setter.invoke(arg, id);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void validateSetterAndGetter(Method setter, Method getter) {
        if (!setter.getParameterTypes()[0].equals(Long.class)) {
            throw new IllegalArgumentException("Invalid property setter, method=" + setter.getName());
        }

        if (!getter.getReturnType().equals(Long.class)) {
            throw new IllegalArgumentException("Invalid property getter, method=" + getter.getName());
        }
    }
}
