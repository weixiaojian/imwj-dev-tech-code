package com.imwj.design.door;

import com.alibaba.fastjson.JSON;
import com.imwj.design.door.annotation.DoDoor;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * DoDoor注解处理器
 * @author wj
 * @create 2023-06-05 16:43
 */
@Aspect
@Component
public class DoJoinPoint {

    private Logger logger = LoggerFactory.getLogger(DoJoinPoint.class);

    @Value("${userIdStr}")
    public String userIdStr;

    @Pointcut("@annotation(com.imwj.design.door.annotation.DoDoor)")
    public void aopPoint(){
    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        // 获取方法内容
        Method method = getMethod(jp);
        // 获取注解中的字段值
        DoDoor door = method.getAnnotation(DoDoor.class);
        String keyValue = getFiledValue(door.key(), jp.getArgs());
        logger.info("door handler method：{} value：{}", method.getName(), keyValue);
        // 获取不到值直接放行
        if (null == keyValue || "".equals(keyValue)) return jp.proceed();
        // 白名单放行
        if(checkUserIdIntercept(keyValue))return jp.proceed();
        // 拦截并返回
        return returnObject(door, method);
    }

    /**
     * 获取方法
     * @param jp
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return getClass(jp).getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp) throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }

    /**
     * 校验用户id是否需要拦截
     * @param userId
     * @return
     */
    private Boolean checkUserIdIntercept(String userId){
        return userIdStr.contains(userId);
    }

    /**
     * 返回对象
     * @param doGate
     * @param method
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object returnObject(DoDoor doGate, Method method) throws IllegalAccessException, InstantiationException {
        Class<?> returnType = method.getReturnType();
        String returnJson = doGate.returnJson();
        if ("".equals(returnJson)) {
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    /**
     * 获取属性值
     * @param filed
     * @param args
     * @return
     */
    private String getFiledValue(String filed, Object[] args) {
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (null == filedValue || "".equals(filedValue)) {
                    filedValue = BeanUtils.getProperty(arg, filed);
                } else {
                    break;
                }
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }
        }
        return filedValue;
    }
}
