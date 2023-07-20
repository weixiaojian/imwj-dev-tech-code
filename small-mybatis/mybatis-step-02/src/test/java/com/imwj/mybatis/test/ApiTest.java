package com.imwj.mybatis.test;

import com.imwj.mybatis.binding.MapperRegistry;
import com.imwj.mybatis.session.SqlSession;
import com.imwj.mybatis.session.defaults.DefaultSqlSession;
import com.imwj.mybatis.session.defaults.DefaultSqlSessionFactory;
import com.imwj.mybatis.test.dao.IUserDao;
import org.junit.Test;

/**
 * @author wj
 * @create 2023-07-20 10:25
 * @description
 */
public class ApiTest {

    @Test
    public void test1(){
        // 1.注册Mapper
        MapperRegistry registry = new MapperRegistry();
        registry.addMapper("com.imwj.mybatis.test.dao");

        // 2.从SqlSession工厂获取session
        DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3.获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        String res  = userDao.queryUserName("10001");
        System.out.println(res);
    }

}
