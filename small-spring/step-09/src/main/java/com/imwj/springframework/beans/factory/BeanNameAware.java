package com.imwj.springframework.beans.factory;

/**
 * 实现此接口，既能感知到所属的 BeanName
 * @author wj
 * @create 2022-11-16 15:41
 */
public interface BeanNameAware extends Aware{

    void setBeanName(String name);
}
