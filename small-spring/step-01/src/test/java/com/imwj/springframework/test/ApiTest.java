package com.imwj.springframework.test;

import com.imwj.springframework.BeanDefinition;
import com.imwj.springframework.BeanFactory;
import com.imwj.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * 测试用例
 * @author wj
 * @create 2022-10-08 17:37
 */

public class ApiTest {

    @Test
    public void test_BeanFactory(){
        // 1.初始化 BeanFactory
        BeanFactory beanFactory = new BeanFactory();

        // 2.注册 bean
        BeanDefinition beanDefinition = new BeanDefinition(new UserService());
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3.获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }

}
