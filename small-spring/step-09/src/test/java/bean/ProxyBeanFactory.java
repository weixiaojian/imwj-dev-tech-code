package bean;

import com.imwj.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 如果这个bean是FactoryBean的话 那么spring在初始化的时候会调用getObject方法  而不是直接初始化bean
 * @author wj
 * @create 2022-11-17 16:35
 */
public class ProxyBeanFactory implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() throws Exception {
        InvocationHandler handler = (proxy, method, args) -> {
            // 添加排除方法
            if ("toString".equals(method.getName())) return this.toString();
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("10001", "上单");
            hashMap.put("10002", "打野");
            hashMap.put("10003", "中单");

            return "你被代理了 " + method.getName() + "：" + hashMap.get(args[0].toString());
        };
        IUserDao iUserDao = (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserDao.class}, handler);
        return iUserDao;
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
