package com.practice.def;

import com.practice.abc.lazyPop.AbcMapperFactory;
import com.practice.abc.lazyPop.AbcMapperProxyHandler;
import org.springframework.beans.factory.annotation.Required;

import java.lang.reflect.Proxy;

/**
 * Created by Eric on 9/13/2015.
 */
public class DefMapperFactory extends AbcMapperFactory {

    private DefShardIdGenerator defShardIdGenerator;

    @Required
    void setDefShardIdGenerator(DefShardIdGenerator defShardIdGenerator) {
        this.defShardIdGenerator = defShardIdGenerator;
    }

    public <T> T resolve(Class<T> mapper) {
        ClassLoader classLoader = mapper.getClassLoader();
        Class<?>[] interfaces = new Class[]{mapper};
        DefMapperProxy handler = new DefMapperProxy(mapper, sqlSession);
        handler.setDefShardIdGenerator(defShardIdGenerator);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }

}
