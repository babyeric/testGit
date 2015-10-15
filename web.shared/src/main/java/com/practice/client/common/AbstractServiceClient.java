package com.practice.client.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Eric on 10/14/2015.
 */
public abstract class AbstractServiceClient implements InitializingBean{
    protected String carbonRoot;
    protected RestTemplate restTemplate = new RestTemplate();

    public String getCarbonRoot() {
        return carbonRoot;
    }

    public void setCarbonRoot(String carbonRoot) {
        this.carbonRoot = carbonRoot;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(carbonRoot);
    }
}
