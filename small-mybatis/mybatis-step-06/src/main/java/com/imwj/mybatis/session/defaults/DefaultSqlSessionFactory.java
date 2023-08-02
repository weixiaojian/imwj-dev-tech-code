package com.imwj.mybatis.session.defaults;

import com.imwj.mybatis.binding.MapperRegistry;
import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.session.SqlSession;
import com.imwj.mybatis.session.SqlSessionFactory;

/**
 * @author wj
 * @create 2023-07-20 10:28
 * @description 默认的 DefaultSqlSessionFactory
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}

