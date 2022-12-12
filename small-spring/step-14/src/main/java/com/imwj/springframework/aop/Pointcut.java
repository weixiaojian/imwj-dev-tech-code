package com.imwj.springframework.aop;


/**
 * 切点表达式接口
 * @author wj
 * @create 2022-11-30 16:42
 */
public interface Pointcut {

    /**
     * 得到切点表达式的类过滤器
     * @return
     */
    ClassFilter getClassFilter();

    /**
     * 得到切点表达式的方法匹配器
     * @return
     */
    MethodMatcher getMethodMatcher();

}
