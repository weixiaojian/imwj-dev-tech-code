package com.imwj.design;

/**
 * 拦截器接口
 * @author wj
 * @create 2023-06-01 17:38
 */
public interface HandlerInterceptor {

    boolean preHandle(String request, String response, Object handler);
}
