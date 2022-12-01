package com.imwj.springframework.aop;

/**
 * 被代理的目标对象
 * @author wj
 * @create 2022-12-01 15:31
 */
public class TargetSource {

    private final Object target;

    /**
     * 初始化构造 传入被代理的目标对象
     * @param target
     */
    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取被代理的目标对象的class
     * @return
     */
    public Class<?>[] getTargetClass(){
        return this.target.getClass().getInterfaces();
    }

    /**
     * 或被代理的目标对象
      * @return
     */
    public Object getTarget(){
        return this.target;
    }

}
