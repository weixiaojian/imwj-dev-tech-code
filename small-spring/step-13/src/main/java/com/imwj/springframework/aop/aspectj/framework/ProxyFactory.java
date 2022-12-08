package com.imwj.springframework.aop.aspectj.framework;

import com.imwj.springframework.aop.AdvisedSupport;
import com.imwj.springframework.aop.framework.AopProxy;
import com.imwj.springframework.aop.framework.Cglib2AopProxy;
import com.imwj.springframework.aop.framework.JdkDynamicAopProxy;

/**
 * 代理工厂类：确定使用jdk代理还是cglib代理
 * @author wj
 * @create 2022-12-07 11:25
 */
public class ProxyFactory {

    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport){
        this.advisedSupport = advisedSupport;
    }

    /**
     * 获取代理对象
     * @return
     */
    public Object getProxy(){
        return createAopProxy().getProxy();
    }

    /**
     * 创建aop带来对象：jdk或cglib
     * @return
     */
    private AopProxy createAopProxy(){
        if(advisedSupport.isProxyTargetClass()){
            return new Cglib2AopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }

}
