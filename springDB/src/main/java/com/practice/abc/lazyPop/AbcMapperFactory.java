package com.practice.abc.lazyPop;

import org.apache.ibatis.session.SqlSession;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/19/15
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbcMapperFactory {

    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public <T> T resolve(Class<T> cls) {
        return AbcMapperProxyHandler.createProxyObject(cls, sqlSession);
    }
}
