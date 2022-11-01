package test;

import bean.UserDao;
import bean.UserService;
import com.imwj.springframework.BeansException;
import com.imwj.springframework.factory.PropertyValue;
import com.imwj.springframework.factory.PropertyValues;
import com.imwj.springframework.factory.config.BeanDefinition;
import com.imwj.springframework.factory.config.BeanReference;
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
        // 2.UserDao注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
        // 3.UserService设置属性[uId,userDao]
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10002"));
        propertyValues.addPropertyValue(new PropertyValue("userDao",new BeanReference("userDao")));
        // 4.UserService注入Bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 5.UserService获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }

}
