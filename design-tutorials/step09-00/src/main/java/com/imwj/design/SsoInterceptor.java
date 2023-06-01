package com.imwj.design;

/**
 * sso登录拦截器
 * @author wj
 * @create 2023-06-01 17:39
 */
public class SsoInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(String request, String response, Object handler) {
        // 模拟获取cookie
        String ticket = request.substring(1, 8);
        // 模拟校验
        return ticket.equals("success");
    }
}
