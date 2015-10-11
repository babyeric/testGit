package com.practice.configuration;

import com.practice.article.ArticleHistoryMapper;
import com.practice.article.ArticleMapper;
import com.practice.db.DataService;
import com.practice.def.*;
import com.practice.reverseLookup.ReverseLookupServiceResolver;
import com.practice.reverseLookup.StringReverseLookupService;
import com.practice.reverseLookup.StringToLongLookupMapper;
import com.practice.site.SiteMapper;
import com.practice.site.SiteMapperImpl;
import com.practice.user.UserMapper;
import com.practice.user.UserMapperImpl;
import com.practice.user.UserPasswordMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.juric.sharding.config.LogicalIdRange;
import org.juric.sharding.config.LogicalRepository;
import org.juric.sharding.config.PhysicalDatabase;
import org.juric.sharding.config.RepositoryConfig;
import org.juric.sharding.datasource.DataSourceFactory;
import org.juric.sharding.datasource.DummyDataSource;
import org.juric.sharding.datasource.ShardingDataSourceManager;
import org.juric.sharding.mapper.ShardingMapperFactory;
import org.juric.sharding.mapper.ShardingMapperUtils;
import org.juric.sharding.transactional.mybatis.session.ShardingSqlSessionTemplate;
import org.juric.sharding.transactional.mybatis.ShardingSpringManagedTransactionFactory;
import org.juric.sharding.transactional.spring.ShardingTransactionManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/10/15
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */

@Configuration
public class DBConfiguration {
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

    @Bean(name="databaseConfig")
    public RepositoryConfig<PhysicalDatabase> databaseConfig() {
        RepositoryConfig<PhysicalDatabase> databaseConfig = new RepositoryConfig<>();
        LogicalRepository<PhysicalDatabase> logicalDatabase = new LogicalRepository("test");
        databaseConfig.addLogicalRepository(logicalDatabase);
        logicalDatabase.addPhysicalRepository(new PhysicalDatabase(new LogicalIdRange(0, 49999), "localhost:" + port, test1));
        logicalDatabase.addPhysicalRepository(new PhysicalDatabase(new LogicalIdRange(50000, 99999), "localhost:" + port, test2));

        logicalDatabase = new LogicalRepository("mapping");
        databaseConfig.addLogicalRepository(logicalDatabase);
        logicalDatabase.addPhysicalRepository(new PhysicalDatabase(new LogicalIdRange(0, 49999), "localhost:" + port, "mapping1"));
        logicalDatabase.addPhysicalRepository(new PhysicalDatabase(new LogicalIdRange(50000, 99999), "localhost:" + port, "mapping2"));

        return databaseConfig;
    }

    @Bean(name="transactionFactory")
    public TransactionFactory transactionFactory() {
        ShardingSpringManagedTransactionFactory transactionFactory = new ShardingSpringManagedTransactionFactory();
        transactionFactory.setDataSourceManager(dataSourceManager());
        return transactionFactory;
    }

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(new DummyDataSource());
        sqlSessionFactory.setMapperLocations(scanner.getResources("classpath:mapper/*.xml"));
        sqlSessionFactory.setTransactionFactory(transactionFactory());
        return sqlSessionFactory.getObject();
    }

    @Bean(name="transactionManager")
    public AbstractPlatformTransactionManager transactionManager() {
        ShardingTransactionManager transactionManager = new ShardingTransactionManager();
        return transactionManager;
    }

    @Bean (name="userMapper")
    public UserMapper userMapper() throws Exception {
        UserMapperImpl ret = new UserMapperImpl();
        ret.setUserMapper(defMapperFactory().resolve(UserMapper.class));
        ret.setStringToLongLookupMapper(stringToLongLookupMapper());
        return ret;
    }

    @Bean (name="articleMapper")
    public ArticleMapper articleMapper() throws Exception {
        return defMapperFactory().resolve(ArticleMapper.class);
    }

    @Bean (name="articleHistoryMapper")
    public ArticleHistoryMapper articleHistoryMapper() throws Exception {
        return defMapperFactory().resolve(ArticleHistoryMapper.class);
    }

    @Bean (name="idGeneratorMapper")
    public IdGeneratorMapper idGeneratorMapper() throws Exception {
        return shardingMapperFactory().resolve(IdGeneratorMapper.class);
    }

    @Bean (name="siteMapper")
    public SiteMapper siteMapper() throws Exception {
        SiteMapperImpl siteMapper = new SiteMapperImpl();
        siteMapper.setSiteMapper(defMapperFactory().resolve(SiteMapper.class));
        siteMapper.setStringToLongLookupMapper(stringToLongLookupMapper());
        return siteMapper;
    }

    @Bean (name="userPasswordMapper")
    public UserPasswordMapper userPasswordMapper() throws Exception {
        return defMapperFactory().resolve(UserPasswordMapper.class);
    }

    @Bean
    public StringToLongLookupMapper stringToLongLookupMapper() throws Exception {
        return shardingMapperFactory().resolve(StringToLongLookupMapper.class);
    }

    @Bean (name="shardingMapperFactory")
    public ShardingMapperFactory shardingMapperFactory() throws Exception {
        SqlSessionTemplate sessionTemplate = new ShardingSqlSessionTemplate(sqlSessionFactory());
        ShardingMapperFactory mapperFactory = new ShardingMapperFactory();
        mapperFactory.setSqlSession(sessionTemplate);
        return mapperFactory;
    }

    @Bean (name="defMapperFactory")
    public DefMapperFactory defMapperFactory() throws Exception {
        SqlSessionTemplate sessionTemplate = new ShardingSqlSessionTemplate(sqlSessionFactory());
        DefMapperFactory mapperFactory = new DefMapperFactory();
        mapperFactory.setSqlSession(sessionTemplate);
        mapperFactory.setDefShardIdGenerator(defShardIdGenerator());
        return mapperFactory;
    }

    @Bean (name="dataSourceManager")
    public ShardingDataSourceManager dataSourceManager() {
        ShardingDataSourceManager dataSourceManager = new ShardingDataSourceManager();
        dataSourceManager.setRepositoryConfig(databaseConfig());
        dataSourceManager.setDataSourceFactory(dataSourceFactory());
        return dataSourceManager;
    }

    @Bean (name="defShardIdGenerator")
    public DefShardIdGenerator defShardIdGenerator() throws Exception {
        DefShardIdGenerator defShardIdGenerator = new DefCachedShardIdGenerator();
        defShardIdGenerator.setBatchSize(20);
        defShardIdGenerator.setIdGeneratorMapper(idGeneratorMapper());
        return defShardIdGenerator;
    }

    @Bean
    public ShardingMapperUtils abcMapperUtils() {
        return new ShardingMapperUtils(databaseConfig(), dataSourceManager());
    }

    @Bean
    public DataSourceFactory dataSourceFactory() {
        return new DataSourceFactoryImpl();
    }

    @Bean
    public ReverseLookupServiceResolver reverseLookupServiceResolver() throws Exception {
        ReverseLookupServiceResolver reverseLookupServiceResolver = new ReverseLookupServiceResolver();
        StringReverseLookupService stringReverseLookupService = new StringReverseLookupService();
        stringReverseLookupService.setStringToLongLookupMapper(shardingMapperFactory().resolve(StringToLongLookupMapper.class));
        reverseLookupServiceResolver.register(String.class, stringReverseLookupService);
        return reverseLookupServiceResolver;
    }
}
