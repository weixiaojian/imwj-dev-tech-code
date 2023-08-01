package com.imwj.mybatis.session.defaults;

import com.imwj.mybatis.binding.MapperRegistry;
import com.imwj.mybatis.session.SqlSession;

/**
 * @author wj
 * @create 2023-07-18 17:45
 * @description 默认的 DefaultSqlSession
 */
public class DefaultSqlSession implements SqlSession {

    /**
     * 映射器注册机
     */
    private MapperRegistry mapperRegistry;

    public DefaultSqlSession(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        System.out.println(statement + "执行selectOne，参数：" + parameter);
        return (T) "test";
    }

    /**
     * 获取代理类
     * @param type Mapper interface class
     * @return
     * @param <T>
     */
    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }
}
