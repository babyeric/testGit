package com.practice.configurer;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.api.storage.path.StoragePathService;
import com.juric.carbon.api.user.UserService;
import com.practice.client.article.ArticleServiceClientImpl;
import com.practice.client.storage.StoragePathServiceClientImpl;
import com.practice.client.user.UserServiceClientImpl;
import org.juric.storage.configurer.StorageConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.ui.velocity.SpringResourceLoader;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Properties;

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
        ArticleServiceClientImpl articleService = new ArticleServiceClientImpl();
        articleService.setCarbonRoot(carbonRoot);
        return articleService;
    }

    @Bean(name="storagePathService")
    public StoragePathService storagePathServiceClient() {
        StoragePathServiceClientImpl storagePathServiceClient = new StoragePathServiceClientImpl();
        storagePathServiceClient.setCarbonRoot(carbonRoot);
        return storagePathServiceClient;
    }

    @Bean (name="userService")
    public UserService userService() {
        UserServiceClientImpl userServiceClient = new UserServiceClientImpl();
        userServiceClient.setCarbonRoot(carbonRoot);
        return userServiceClient;
    }
}
