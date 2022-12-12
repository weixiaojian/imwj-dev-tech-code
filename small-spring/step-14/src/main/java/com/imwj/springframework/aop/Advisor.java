package com.imwj.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * 需要增强的逻辑
 * @author wj
 * @create 2022-12-07 11:03
 */
public interface Advisor {


    /**
     * 返回一个处理器（方法前或方法后等）
     * @return
     */
    Advice getAdvice();

}
