package com.juric.carbon.configuration;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.api.site.SiteService;
import com.juric.carbon.api.storage.path.StoragePathService;
import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.api.user.UserService;
import com.juric.carbon.service.article.ArticleServiceImpl;
import com.juric.carbon.service.site.SiteServiceImpl;
import com.juric.carbon.service.storage.StoragePathServiceImpl;
import com.juric.carbon.service.user.UserPasswordServiceImpl;
import com.juric.carbon.service.user.UserServiceImpl;
import com.juric.carbon.service.user.password.HashVersion;
import com.practice.article.ArticleMapper;
import com.practice.configuration.DBConfiguration;
import com.practice.def.DefShardIdGenerator;
import com.practice.site.SiteMapper;
import com.practice.user.UserMapper;
import com.practice.user.UserPasswordMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/1/15
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Import(DBConfiguration.class)
@Configuration
public class CarbonConfiguration {
    @Resource(name="defShardIdGenerator")
    private DefShardIdGenerator defShardIdGenerator;

    @Resource(name = "articleMapper")
    private ArticleMapper articleMapper;

    @Resource(name = "siteMapper")
    private SiteMapper siteMapper;

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    @Resource(name = "userPasswordMapper")
    private UserPasswordMapper userPasswordMapper;

    public void setDefShardIdGenerator(DefShardIdGenerator defShardIdGenerator) {
        this.defShardIdGenerator = defShardIdGenerator;
    }

    @Bean(name="storagePathService")
    public StoragePathService storagePathService() {
        StoragePathServiceImpl storagePathService = new StoragePathServiceImpl();
        storagePathService.setIdGenerator(defShardIdGenerator);
        return storagePathService;
    }

    @Bean(name="articleService")
    public ArticleService articleService() {
        ArticleServiceImpl articleService = new ArticleServiceImpl();
        articleService.setArticleMapper(articleMapper);
        return articleService;
    }

    @Bean(name="siteService")
    public SiteService siteService() {
        SiteServiceImpl siteService = new SiteServiceImpl();
        siteService.setSiteMapper(siteMapper);
        return siteService;
    }

    @Bean(name="userService")
    public UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserMapper(userMapper);
        userService.setUserPasswordService(userPasswordService());
        return userService;
    }

    @Bean(name="userPasswordService")
    public UserPasswordService userPasswordService() {
        UserPasswordServiceImpl userPasswordService = new UserPasswordServiceImpl();
        userPasswordService.setUserPasswordMapper(userPasswordMapper);
        userPasswordService.setUserMapper(userMapper);
        userPasswordService.setHashVersion(HashVersion.SHA512_1000);
        return userPasswordService;
    }
}
