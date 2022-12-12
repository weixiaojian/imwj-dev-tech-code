package com.imwj.springframework.context;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.Aware;

/**
 * 实现此接口，既能感知到所属的 ApplicationContext
 * @author wj
 * @create 2022-11-16 15:42
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContextAware(ApplicationContext applicationContext) throws BeansException;
}
