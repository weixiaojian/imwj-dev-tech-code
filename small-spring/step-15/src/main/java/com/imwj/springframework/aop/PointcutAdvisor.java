package com.imwj.springframework.aop;

/**
 * 切入点需要增强的逻辑
 * @author wj
 * @create 2022-12-07 11:09
 */
public interface PointcutAdvisor extends Advisor{


    /**
     * 获取程序的切入点
     * 1.PointcutAdvisor 承担了 Pointcut 和 Advice 的组合
     * 2.Pointcut 用于获取 JoinPoint
     * 3.Advice 决定于 JoinPoint 执行什么操作。
     * @return
     */
    Pointcut getPointcut();

}
