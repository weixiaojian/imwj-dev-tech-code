package com.imwj.design.test;

import com.imwj.design.chaannel.Pay;
import com.imwj.design.chaannel.WxPay;
import com.imwj.design.chaannel.ZfbPay;
import com.imwj.design.mode.PayFaceMode;
import com.imwj.design.mode.PayFingerprintMode;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author wj
 * @create 2023-05-31 16:32
 */
public class ApiTest {

    @Test
    public void test(){

        System.out.println("\r\n模拟测试场景；微信支付、人脸方式。");
        Pay wxPay = new WxPay(new PayFaceMode());
        wxPay.transfer("weixin_1092033111", "100000109893", new BigDecimal(100));

        System.out.println("\r\n模拟测试场景；支付宝支付、指纹方式。");
        Pay zfbPay = new ZfbPay(new PayFingerprintMode());
        zfbPay.transfer("jlu19dlxo111", "100000109894", new BigDecimal(100));
    }

}
