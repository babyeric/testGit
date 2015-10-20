package com.practice.configurer;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.api.site.SiteService;
import com.juric.carbon.api.storage.path.StoragePathService;
import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.api.user.UserService;
import com.practice.client.article.ArticleServiceClient;
import com.practice.client.site.SiteServiceClient;
import com.practice.client.storage.StoragePathServiceClient;
import com.practice.client.user.UserPasswordServiceClient;
import com.practice.client.user.UserServiceClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/25/15
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class AppConfiguration {

    @Value("${carbon.service.rest.address}")
    private String carbonRoot;

    @Bean(name="articleService")
    public ArticleService articleService() {
        ArticleServiceClient articleService = new ArticleServiceClient();
        articleService.setCarbonRoot(carbonRoot);
        return articleService;
    }

    @Bean(name="storagePathService")
    public StoragePathService storagePathServiceClient() {
        StoragePathServiceClient storagePathServiceClient = new StoragePathServiceClient();
        storagePathServiceClient.setCarbonRoot(carbonRoot);
        return storagePathServiceClient;
    }

    @Bean (name="userService")
    public UserService userService() {
        UserServiceClientImpl userServiceClient = new UserServiceClientImpl();
        userServiceClient.setCarbonRoot(carbonRoot);
        return userServiceClient;
    }

    @Bean (name="userPasswordService")
    public UserPasswordService userPasswordService() {
        UserPasswordServiceClient userPasswordServiceClient = new UserPasswordServiceClient();
        userPasswordServiceClient.setCarbonRoot(carbonRoot);
        return userPasswordServiceClient;
    }

    @Bean (name="siteService")
    public SiteService siteService() {
        SiteServiceClient siteServiceClient = new SiteServiceClient();
        siteServiceClient.setCarbonRoot(carbonRoot);
        return siteServiceClient;
    }
}
