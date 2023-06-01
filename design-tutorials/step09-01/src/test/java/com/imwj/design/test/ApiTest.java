package com.imwj.design.test;

import com.imwj.design.LoginSsoDecorator;
import org.junit.Test;

/**
 * @author wj
 * @create 2023-06-01 17:44
 */
public class ApiTest {

    @Test
    public void test_LoginSsoDecorator() {
        LoginSsoDecorator ssoDecorator = new LoginSsoDecorator();
        String request = "1successhuahua";
        boolean success = ssoDecorator.preHandle(request, "ewcdqwt40liuiu", "t");
        System.out.println("登录校验：" + request + (success ? " 放行" : " 拦截"));
    }

}
