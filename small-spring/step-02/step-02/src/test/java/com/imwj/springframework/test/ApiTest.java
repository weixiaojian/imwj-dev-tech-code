package com.imwj.springframework.test;

import com.imwj.springframework.BeansException;
import com.imwj.springframework.factory.config.BeanDefinition;
import com.imwj.springframework.factory.support.DefaultListableBeanFactory;
import com.imwj.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * 测试用例
 * @author wj
 * @create 2022-10-08 17:37
 */

public class ApiTest {

    @Test
    public void test_BeanFactory() throws BeansException {
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 3.第一次获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
        // 4.第二次获取bean
        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
        userService_singleton.queryUserInfo();
        // 5.比较两个bean是否相等
        System.out.println(userService == userService_singleton);
    }

}
