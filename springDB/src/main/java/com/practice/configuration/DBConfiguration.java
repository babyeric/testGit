package com.practice.configuration;

import com.practice.abc.lazyPop.AbcDataSourceManager;
import com.practice.abc.lazyPop.AbcDummyDataSource;
import com.practice.abc.lazyPop.AbcMapperFactory;
import com.practice.abc.lazyPop.AbcMapperUtils;
import com.practice.abc.lazyPop.config.DataSourceConfig;
import com.practice.abc.lazyPop.config.LogicalDatabase;
import com.practice.abc.lazyPop.config.LogicalIdRange;
import com.practice.abc.lazyPop.config.PhysicalShard;
import com.practice.abc.transactional.mybatis.AbcSpringManagedTransactionFactory;
import com.practice.abc.transactional.spring.AbcTransactionManager;
import com.practice.db.DataService;
import com.practice.def.DefCachedShardIdGenerator;
import com.practice.def.DefMapperFactory;
import com.practice.def.DefShardIdGenerator;
import com.practice.def.IdGeneratorMapper;
import com.practice.user.UserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/10/15
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */

@Configuration
public class DBConfiguration{
    @Value("${mysql.port}")
    private String port;

    @Value("${mysql.test1}")
    private String test1;

    @Value("${mysql.test2}")
    private String test2;

    @Bean(name="dataService2")
    public DataService dataService() {
        return new DataService();
    }

    @Bean(name="dataSourceConfig")
    public DataSourceConfig databaseConfig() {
        DataSourceConfig databaseConfig = new DataSourceConfig();
        LogicalDatabase logicalDatabase = new LogicalDatabase("test");
        databaseConfig.addLogicalDatabase(logicalDatabase);
        logicalDatabase.addPhysicalShard(new PhysicalShard(new LogicalIdRange(0, 49999), "localhost:" + port, test1));
        logicalDatabase.addPhysicalShard(new PhysicalShard(new LogicalIdRange(50000, 99999), "localhost:" + port, test2));
        return databaseConfig;
    }

    @Bean(name="transactionFactory")
    public TransactionFactory transactionFactory() {
        AbcSpringManagedTransactionFactory transactionFactory = new AbcSpringManagedTransactionFactory();
        transactionFactory.setDataSourceManager(dataSourceManager());
        return transactionFactory;
    }

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(new AbcDummyDataSource());
        sqlSessionFactory.setMapperLocations(new Resource[] {new ClassPathResource("mapper/userMapper.xml"),new ClassPathResource("mapper/IdGeneratorMapper.xml")});
        sqlSessionFactory.setTransactionFactory(transactionFactory());
        return sqlSessionFactory.getObject();
    }

    @Bean(name="transactionManager")
    public AbstractPlatformTransactionManager transactionManager() {
        AbcTransactionManager transactionManager = new AbcTransactionManager();
        return transactionManager;
    }

    @Bean (name="userMapper")
    public UserMapper userMapper() throws Exception {
        return mapperFactory().resolve(UserMapper.class);
    }

    @Bean (name="idGeneratorMapper")
    public IdGeneratorMapper idGeneratorMapper() {
        try {
            return mapperFactory().resolve(IdGeneratorMapper.class);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    @Bean (name="mapperFactory")
    public AbcMapperFactory mapperFactory() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        DefMapperFactory mapperFactory = new DefMapperFactory();
        mapperFactory.setSqlSession(sessionTemplate);
        mapperFactory.setDefShardIdGenerator(defShardIdGenerator());
        return mapperFactory;
    }

    @Bean (name="dataSourceManager")
    public AbcDataSourceManager dataSourceManager() {
        AbcDataSourceManager dataSourceManager = new AbcDataSourceManager();
        dataSourceManager.setDataSourceConfig(databaseConfig());
        return dataSourceManager;
    }

    @Bean (name="defShardIdGenerator")
    public DefShardIdGenerator defShardIdGenerator() {
        DefShardIdGenerator defShardIdGenerator = new DefCachedShardIdGenerator();
        defShardIdGenerator.setBatchSize(20);
        return defShardIdGenerator;
    }

    @Bean
    public AbcMapperUtils abcMapperUtils() {
        return new AbcMapperUtils(databaseConfig(), dataSourceManager());
    }
}
