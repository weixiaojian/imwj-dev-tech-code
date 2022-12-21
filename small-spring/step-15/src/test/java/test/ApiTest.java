package test;


import bean.IUserService;
import bean.UserService;
import com.imwj.springframework.aop.framework.ReflectiveMethodInvocation;
import com.imwj.springframework.context.support.ClassPathXmlApplicationContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wj
 * @create 2022-10-11 17:29
 */
public class ApiTest {

    /**
     * 单元测试(占位符)
     */
    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }


}
