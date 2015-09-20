package org.juric.sharding.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/20/15
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummyDataSource implements DataSource {
    @Override
    public Connection getConnection() throws SQLException {
        ClassLoader classLoader = Connection.class.getClassLoader();
        Class<?>[] interfaces = new Class[] { Connection.class };
        return (Connection) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
