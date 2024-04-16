package com.imwj.ltzf;

import com.alibaba.fastjson.JSON;
import com.imwj.ltzf.factory.Configuration;
import com.imwj.ltzf.factory.defaults.DefaultPayFactory;
import com.imwj.ltzf.payments.nativepay.NativePayService;
import com.imwj.ltzf.payments.nativepay.model.PrepayRequest;
import com.imwj.ltzf.payments.nativepay.model.PrepayResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wj
 * @create 2024-04-16 17:34
 * @description 扫码支付service测试
 */
@Slf4j
public class PayServiceTest {

    private NativePayService nativePayService;


    @Before
    public void init(){
        // 1.初始化配置类(appid、merchantId、partnerKey)
        Configuration configuration = new Configuration("1104268", "1673424392", "6d3e889f359fcb83d150e9553a9217b9");
        // 2.初始化默认的创建工厂(http客户端配置:请求超时时间等)
        DefaultPayFactory payFactory = new DefaultPayFactory(configuration);
        // 3.初始化Retrofit(baseUrl)、创建nativePayService
        this.nativePayService = payFactory.nativePayService();
    }

    @Test
    public void test_prepay() throws Exception{
        // 4.封装请求参数
        PrepayRequest request = new PrepayRequest();
        request.setMchid("1673424392");
        request.setOutTradeNo(RandomStringUtils.randomNumeric(8));
        request.setTotalFee("0.01");
        request.setBody("IPHONE 15 PRO MAX");
        request.setNotifyUrl("https://bolg.imwj.club/");

        // 5.创建支付订单
        log.info("请求参数:{}", JSON.toJSONString(request));
        PrepayResponse response = nativePayService.prepay(request);
        log.info("响应参数:{}", JSON.toJSONString(response));
    }

}
