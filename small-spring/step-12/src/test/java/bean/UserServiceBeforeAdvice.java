package bean;

import com.imwj.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 业务拦截类
 * @author wj
 * @create 2022-12-07 16:22
 */
public class UserServiceBeforeAdvice  implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("方法前 打印日志：" + method.getName());
    }
}
