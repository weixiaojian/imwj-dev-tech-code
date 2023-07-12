package com.imwj.mybatis.binding;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author wj
 * @create 2023-07-11 17:09
 * @description 映射代理器工厂
 */
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * 获取代理对象
     * @param sqlSession
     * @return
     */
    public T newInstance(Map<String, String> sqlSession){
        final MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession, mapperInterface);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
