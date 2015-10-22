package com.practice.configuration;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Eric on 10/18/2015.
 */
@Configuration
public class WebMvcConfigurer2 extends WebMvcConfigurerAdapter {
    @Value("${http.server.connector.maxPostSize}")
    private int maxPostSize;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new CSRFAddingHandlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof TomcatEmbeddedServletContainerFactory) {
                    TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
                    tomcat.addConnectorCustomizers(new TomcatConnectorCustomizer() {
                        @Override
                        public void customize(Connector connector) {
                            connector.setMaxPostSize(maxPostSize);
                        }
                    });
                }
            }
        };
    }


}
