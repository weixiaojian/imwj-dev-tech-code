package com.imwj.springframework.aop.aspectj;

import com.imwj.springframework.aop.ClassFilter;
import com.imwj.springframework.aop.MethodMatcher;
import com.imwj.springframework.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 方法表达式切入点
 * @author wj
 * @create 2022-11-28 17:41
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher  {

    /**
     * 支持的切入点表达式
     */
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();

    /**
     * 默认支持execution表达式类型
     */
    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    /**
     * 切入点表达式
     */
    private final PointcutExpression pointcutExpression;

    /**
     * aop表达式切入点
     * @param expression 表达式
     */
    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
