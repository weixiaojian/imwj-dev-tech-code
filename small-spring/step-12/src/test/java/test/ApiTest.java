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

    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut methodMatcher = new AspectJExpressionPointcut("execution(* bean.IUserService.*(..))");
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(methodMatcher.matches(clazz));
        System.out.println(methodMatcher.matches(method, clazz));

    }

    @Test
    public void test_proxy_method() {
        // 目标对象
        Object targetObj = new UserService();
        // 0.aop代理
        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
            // 2.方法匹配器
            MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* bean.IUserService.*(..))");
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 3.如果方法匹配是目标对象的
                if (methodMatcher.matches(method, targetObj.getClass())) {
                    // 5.设置方法拦截器
                    MethodInterceptor methodInterceptor = invocation -> {
                        long start = System.currentTimeMillis();
                        try {
                            // 执行代理方法
                            return invocation.proceed();
                        } finally {
                            // 增加额外操作如：日志、监控等
                            System.out.println("监控 - Begin By AOP");
                            System.out.println("方法名称：" + invocation.getMethod().getName());
                            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
                            System.out.println("监控 - End\r\n");
                        }
                    };
                    // 4.通过反射调用代理对象的方法
                    return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                }
                // 调用被代理的方法（执行了代理方法就不会执行该步骤）
                return method.invoke(targetObj, args);
            }
        });
        // 1.执行业务逻辑
        String result = proxy.queryUserInfo();
        // 6.输出结果
        System.out.println("测试结果：" + result);
    }

    /**
     * 动态代理测试
     */
    @Test
    public void test_dynamic() {
        // 目标对象
        IUserService userService = new UserService();

        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* bean.IUserService.*(..))"));

        // 原生对象调用
        System.out.println("原生调用：" + userService.queryUserInfo());

        // 代理对象(JdkDynamicAopProxy)
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("JdkDynamicAopProxy测试结果：" + proxy_jdk.queryUserInfo());

        // 代理对象(Cglib2AopProxy)
        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("Cglib2AopProxy测试结果：" + proxy_cglib.register("花花"));
    }

}
