package bean;

import com.imwj.springframework.aop.MethodAfterAdvice;
import com.imwj.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 业务拦截类
 * @author wj
 * @create 2022-12-07 16:22
 */
public class UserServiceAfterAdvice implements MethodAfterAdvice {

    @Override
    public void after(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("方法后 打印日志：" + method.getName());
    }
}
