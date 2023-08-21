package com.imwj.mybatis.test;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.imwj.mybatis.binding.MapperRegistry;
import com.imwj.mybatis.io.Resources;
import com.imwj.mybatis.reflection.MetaObject;
import com.imwj.mybatis.reflection.SystemMetaObject;
import com.imwj.mybatis.session.SqlSession;
import com.imwj.mybatis.session.SqlSessionFactory;
import com.imwj.mybatis.session.SqlSessionFactoryBuilder;
import com.imwj.mybatis.session.defaults.DefaultSqlSession;
import com.imwj.mybatis.session.defaults.DefaultSqlSessionFactory;
import com.imwj.mybatis.test.dao.IUserDao;
import com.imwj.mybatis.test.entity.User;
import lombok.Data;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @create 2023-07-20 10:25
 * @description
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_SqlSessionFactory() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        User user = userDao.queryUserInfoById("1");
        logger.info("测试结果：{}", JSON.toJSONString(user));
    }

}
