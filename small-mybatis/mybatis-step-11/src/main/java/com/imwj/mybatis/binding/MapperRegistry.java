package com.imwj.mybatis.binding;

import cn.hutool.core.lang.ClassScanner;
import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wj
 * @create 2023-07-18 17:31
 * @description 映射器注册
 */
public class MapperRegistry {

    private Configuration config;

    public MapperRegistry(Configuration configuration) {
        this.config = config;
    }

    /**
     * 将已添加映射器代理加入到HashMap
     */
    private final Map<Class<?>, MapperProxyFactory> knownMappers = new HashMap<>();

    public <T> T getMapper(Class<T> type, SqlSession sqlSession){
        final MapperProxyFactory<T> mapperProxyFactory =  (MapperProxyFactory<T>) knownMappers.get(type);
        if(mapperProxyFactory == null){
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        }catch (Exception e){
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    /**
     * 加载指定路径的接口
     * @param type
     * @param <T>
     */
    public <T> void addMapper(Class<T>  type){
        /* Mapper 必须是接口才会注册 */
        if(type.isInterface()){
            // 不允许重复添加
            if(knownMappers.containsKey(type)){
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            // 注册映射器代理工厂
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    /**
     * 加载指定包下的所有接口
     * @param packageName
     */
    public void addMapper(String packageName){
        Set<Class<?>> mapperSet = ClassScanner.scanPackage(packageName);
        for(Class<?> mapperClass : mapperSet){
            addMapper(mapperClass);
        }
    }

    /**
     * 判断类是否已经存在
     * @param type
     * @return
     * @param <T>
     */
    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }
}
