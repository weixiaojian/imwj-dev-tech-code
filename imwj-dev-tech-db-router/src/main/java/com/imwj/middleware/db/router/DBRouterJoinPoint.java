package com.imwj.middleware.db.router;

import com.imwj.middleware.db.router.annotation.DBRouter;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wj
 * @create 2024-08-08 10:46
 * @description 自定义路由注解解析类
 */
@Aspect
@Component("db-router-point")
public class DBRouterJoinPoint {

    private Logger logger = LoggerFactory.getLogger(DBRouterJoinPoint.class);

    @Autowired
    private DBRouterConfig dbRouterConfig;

    @Pointcut("@annotation(com.imwj.middleware.db.router.annotation.DBRouter)")
    public void aopPoint(){

    }

    @Around("aopPoint() && @annotation(dbRouter)")
    public Object doRouter(ProceedingJoinPoint jp, DBRouter dbRouter) throws Throwable {
        String dbKey = dbRouter.key();
        if(StringUtils.isBlank(dbKey)){
            throw new RuntimeException("annotation DBRouter key is null!");
        }
        // 计算路由
        String dbKeyAttr = getAttrValue(dbKey, jp.getArgs());
        int size = dbRouterConfig.getDbCount() * dbRouterConfig.getTbCount();
        // 扰动函数
        int idx = (size - 1) & (dbKeyAttr.hashCode() ^ (dbKeyAttr.hashCode() >>> 16));
        // 库表索引
        int dbIdx = idx / dbRouterConfig.getDbCount() + 1;
        int tbIdx = idx / dbRouterConfig.getTbCount() * (dbIdx - 1);
        // 设置到Threadlocal
        DBContextHolder.setDBKey(String.format("%02d", dbIdx));
        DBContextHolder.setTBKey(String.format("%02d", tbIdx));
        logger.info("数据库路由 method：{} dbIdx：{} tbIdx：{}", getMethod(jp).getName(), dbIdx, tbIdx);
        // 执行方法，并清理Threadlocal值
        try {
            return jp.proceed();
        } finally {
            DBContextHolder.clearDBKey();
            DBContextHolder.clearTBKey();
        }
    }

    /**
     * 获取注解下的方法名
     * @param jp
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }


    /**
     * 获取数组中指定属性的值
     * @param attr
     * @param args
     * @return
     */
    private String getAttrValue(String attr, Object[] args){
        String filedValue = null;
        for(Object arg : args){
            try {
                if(StringUtils.isNotBlank(filedValue)){
                    break;
                }
                filedValue = BeanUtils.getProperty(arg, attr);
            } catch (Exception e) {
                logger.error("获取路由属性值失败 attr：{}", attr, e);
            }
        }
        return filedValue;
    }
}
