package com.imwj.design.test;

import com.imwj.design.PayController;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author wj
 * @create 2023-05-31 15:52
 */
public class ApiTest {

    private PayController payController = new PayController();

    @Test
    public void test(){
        // 微信支付
        payController.doPay("10001", "20001", BigDecimal.valueOf(1000), 1,1);

        // 支付宝支付
        payController.doPay("10001", "20001", BigDecimal.valueOf(1000), 2,2);
    }
}
