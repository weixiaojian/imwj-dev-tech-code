package test;


import bean.IUserService;
import bean.UserService;
import bean.UserServiceInterceptor;
import com.imwj.springframework.aop.AdvisedSupport;
import com.imwj.springframework.aop.MethodMatcher;
import com.imwj.springframework.aop.TargetSource;
import com.imwj.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.imwj.springframework.aop.framework.Cglib2AopProxy;
import com.imwj.springframework.aop.framework.JdkDynamicAopProxy;
import com.imwj.springframework.aop.framework.ReflectiveMethodInvocation;
import com.imwj.springframework.context.support.ClassPathXmlApplicationContext;
import event.CustomEvent;
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
    public void test_property() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-property.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService);
    }

    /**
     * 单元测试(包扫描)
     */
    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }



}
