package com.imwj.design.test;

import com.alibaba.fastjson.JSON;
import com.imwj.design.entity.User;
import com.imwj.design.mediator.Resources;
import com.imwj.design.mediator.SqlSession;
import com.imwj.design.mediator.SqlSessionFactory;
import com.imwj.design.mediator.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;

/**
 * @author wj
 * @create 2023-06-12 18:16
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_queryUserInfoById() {
        String resource = "mybatis-config-datasource.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlMapper.openSession();
            try {
                User user = session.selectOne("com.imwj.design.dao.IUserDao.queryUserInfoById", "62baae12-41cb-11eb-8d21-00163e0cd193");
                logger.info("测试结果：{}", JSON.toJSONString(user));
            } finally {
                session.close();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
