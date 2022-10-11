package com.imwj.springframework.test;

import com.imwj.springframework.BeansException;
import com.imwj.springframework.bean.UserService;
import com.imwj.springframework.factory.config.BeanDefinition;
import com.imwj.springframework.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

/**
 * @author wj
 * @create 2022-10-11 17:29
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() throws BeansException {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3.获取bean
        UserService userService = (UserService) beanFactory.getBean("userService", "imwj");
        userService.queryUserInfo();
    }

}
