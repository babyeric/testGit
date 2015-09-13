package com.practice.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Created by Eric on 9/12/2015.
 */
public class EnvironmentAwarePropertyConfigurer extends PropertySourcesPlaceholderConfigurer implements BeanFactoryPostProcessor {
    public final static String APPLICATION_CLUSTER_PROPERTY_NAME = "application.cluster";
    public final static String APPLICATION_ENV_PROPERTY_NAME = "application.env";
    public final static String APPLICATION_CLUSTER_DEFAULT_VALUE = "default";
    public final static String APPLICATION_ENV_DEFAULT_VALUE = "default";
    public final static String CLUSTER_MARCO_NAME = "{C}";
    public final static String ENVIRONMENT_MARCO_NAME = "{E}";
    public final static String[] PROPERTY_TEMPLATES = {"default.default","{C}.default","default.{E}","{C}.{E}"};
    public final static String PATH_TEMPLATE = "config/%s/%s.properties";
    public final static String LOCAL_PROPERTY_NAME = "localProperties";


    private List<String> propertyBags;
    private Environment environment;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setPropertyBags(List<String> propertyBags) {
        this.propertyBags = propertyBags;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        MutablePropertySources propertySources = new MutablePropertySources();
        addEnvironemntProperties(propertySources);
        try {
            addPageProperties(propertySources);
        } catch (IOException var3) {
            throw new BeanInitializationException("Could not load properties", var3);
        }

        setPropertySources(propertySources);
        super.postProcessBeanFactory(beanFactory);
    }

    protected void addEnvironemntProperties(MutablePropertySources propertySources) {

        if(this.environment != null) {
            propertySources.addLast(new PropertySource(ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME, this.environment) {
                public String getProperty(String key) {
                    return ((Environment) this.source).getProperty(key);
                }
            });
        }
    }

    private void addPageProperties(MutablePropertySources propertySources) throws IOException {
        if (propertyBags == null || propertyBags.isEmpty()) {
            return;
        }

        PropertySourcesPropertyResolver propertySourcesPropertyResolver = new PropertySourcesPropertyResolver(propertySources);
        String cluster = propertySourcesPropertyResolver.getProperty(APPLICATION_CLUSTER_PROPERTY_NAME, APPLICATION_CLUSTER_DEFAULT_VALUE);
        String env = propertySourcesPropertyResolver.getProperty(APPLICATION_ENV_PROPERTY_NAME, APPLICATION_ENV_DEFAULT_VALUE);

        LinkedHashSet<String> propertyResources = new LinkedHashSet<>();
        for(String template : PROPERTY_TEMPLATES) {
            propertyResources.add(resolvePropertyResourceName(template, cluster, env));
        }

        propertySources.addLast(new PropertiesPropertySource(LOCAL_PROPERTY_NAME, loadProperties(propertyResources)));
    }

    private Properties loadProperties(Iterable<String> propertyResources) throws IOException {
        Properties properties = new Properties();
        for(String propertyResource : propertyResources) {
            List<Resource> locations = new ArrayList<>();
            for (String propertyBag : propertyBags) {
                Resource resource = new ClassPathResource(String.format(PATH_TEMPLATE, propertyBag, propertyResource));
                if (resource.exists()) {
                    locations.add(resource);
                }
            }
            loadPropertyResources(properties, locations.toArray(new Resource[0]));
        }
        return properties;
    }

    private void loadPropertyResources(Properties properties, Resource ...resources) throws IOException {
        setLocations(resources);
        loadProperties(properties);
        setLocations(null);
    }

    private static String resolvePropertyResourceName(String template, String cluster, String env) {
        String ret = template.replace(CLUSTER_MARCO_NAME, cluster);
        return ret.replace(ENVIRONMENT_MARCO_NAME, env);
    }
}
