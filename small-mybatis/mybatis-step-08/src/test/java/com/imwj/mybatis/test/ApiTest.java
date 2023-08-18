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
    public void test_reflection() throws IOException {
        Teacher teacher = new Teacher();
        List<Teacher.Student> list = new ArrayList<>();
        list.add(new Teacher.Student());
        teacher.setName("imwj");
        teacher.setStudents(list);

        MetaObject metaObject = SystemMetaObject.forObject(teacher);

        logger.info("getGetterNames：{}", JSON.toJSONString(metaObject.getGetterNames()));
        logger.info("getSetterNames：{}", JSON.toJSONString(metaObject.getSetterNames()));
        logger.info("name的get方法返回值：{}", JSON.toJSONString(metaObject.getGetterType("name")));
        logger.info("students的set方法参数值：{}", JSON.toJSONString(metaObject.getGetterType("students")));
        logger.info("name的hasGetter：{}", metaObject.hasGetter("name"));
        logger.info("student.id（属性为对象）的hasGetter：{}", metaObject.hasGetter("student.id"));
        logger.info("获取name的属性值：{}", metaObject.getValue("name"));
        // 重新设置属性值
        metaObject.setValue("name", "小白");
        logger.info("设置name的属性值：{}", metaObject.getValue("name"));
        // 设置属性（集合）的元素值
        metaObject.setValue("students[0].id", "001");
        logger.info("获取students集合的第一个元素的属性值：{}", JSON.toJSONString(metaObject.getValue("students[0].id")));
        logger.info("对象的序列化：{}", JSON.toJSONString(teacher));
    }

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

    @Data
    static class Teacher {

        private String name;

        private double price;

        private List<Student> students;

        private Student student;

        @Data
        public static class Student {
            private String id;
        }

    }
}
