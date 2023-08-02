package com.imwj.mybatis.session;

/**
 * @author wj
 * @create 2023-07-18 17:34
 * @description 工厂模式接口，构建SqlSession的工厂
 */
public interface SqlSessionFactory {


    /**
     * 打开一个 session
     * @return SqlSession
     */
    SqlSession openSession();

}
