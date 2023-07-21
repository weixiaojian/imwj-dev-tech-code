package com.imwj.mybatis.session.defaults;

import com.imwj.mybatis.binding.MapperRegistry;
import com.imwj.mybatis.mapping.MappedStatement;
import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.session.SqlSession;

/**
 * @author wj
 * @create 2023-07-18 17:45
 * @description 默认的 DefaultSqlSession
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        return (T) ("你被代理了！" + "\n方法：" + statement + "\n入参：" + parameter + "\n待执行SQL：" + mappedStatement.getSql());
    }

    /**
     * 获取代理类
     * @param type Mapper interface class
     * @return
     * @param <T>
     */
    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
