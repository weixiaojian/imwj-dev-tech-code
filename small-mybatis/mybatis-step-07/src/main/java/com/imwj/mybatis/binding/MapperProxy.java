package com.imwj.mybatis.binding;

import com.imwj.mybatis.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author wj
 * @create 2023-07-11 17:08
 * @description 映射代理器
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -6424540398559729838L;

    /**
     * sqlSession存储器
     */
    private SqlSession sqlSession;
    /**
     * 代理类
     */
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    /**
     * 执行代理方法
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Object的toString()等相关方法不需要代理  直接执行
        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this, args);
        }else{
            System.out.println("你被代理了");
            return sqlSession.selectOne(mapperInterface.getName() + "." + method.getName(), args);
        }
    }
}
