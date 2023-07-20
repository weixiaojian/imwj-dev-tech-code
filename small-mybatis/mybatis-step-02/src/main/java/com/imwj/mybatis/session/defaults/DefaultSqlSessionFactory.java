package com.imwj.mybatis.session.defaults;

import com.imwj.mybatis.binding.MapperRegistry;
import com.imwj.mybatis.session.SqlSession;
import com.imwj.mybatis.session.SqlSessionFactory;

/**
 * @author wj
 * @create 2023-07-20 10:28
 * @description 默认的 DefaultSqlSessionFactory
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }

}

