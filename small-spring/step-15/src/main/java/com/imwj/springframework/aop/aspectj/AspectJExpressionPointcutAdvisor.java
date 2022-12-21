package com.imwj.springframework.aop.aspectj;

import com.imwj.springframework.aop.Pointcut;
import com.imwj.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * 表达式和切面、拦截方法组合类
 * 实现了 PointcutAdvisor 接口，把切面 pointcut、拦截方法 advice 和具体的拦截表达式包装在一起
 * 这样就可以在 xml 的配置中定义一个 pointcutAdvisor 切面拦截器了
 * @author wj
 * @create 2022-12-07 11:11
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    /**
     * 切面
     */
    private AspectJExpressionPointcut pointcut;
    /**
     * 具体的拦截方法
     */
    private Advice advice;
    /**
     * 表达式
     */
    private String expression;


    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if(null == pointcut){
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
