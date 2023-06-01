package com.imwj.design;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wj
 * @create 2023-06-01 17:42
 */
public class LoginSsoDecorator extends SsoInterceptor {

    private static Map<String, String> authMap = new ConcurrentHashMap<String, String>();

    /**
     * 需要校验的方法
     */
    static {
        authMap.put("huahua", "queryUserInfo");
        authMap.put("doudou", "queryUserInfo");
    }

    @Override
    public boolean preHandle(String request, String response, Object handler) {
        // 原先的逻辑
        String ticket = request.substring(1, 8);
        boolean success = ticket.equals("success");

        // 自己新增的校验逻辑
        if (!success) return false;
        String userId = request.substring(8);
        String method = authMap.get(userId);
        // 模拟方法校验
        return "queryUserInfo".equals(method);
    }
}
