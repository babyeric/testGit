package org.juric.sharding.mapper;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/19/15
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShardingMapperFactory {

    protected SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public <T> T resolve(Class<T> mapper) {
        ClassLoader classLoader = mapper.getClassLoader();
        Class<?>[] interfaces = new Class[]{mapper};
        ShardingMapperProxy handler = new ShardingMapperProxy(mapper, sqlSession);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }
}
