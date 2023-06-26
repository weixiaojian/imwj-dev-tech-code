package com.imwj.design.test;

import com.imwj.design.NetMall;
import com.imwj.design.impl.JDNetMall;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wj
 * @create 2023-06-26 17:45
 */
public class ApiTest {

    public Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_NetMall() {
        NetMall netMall = new JDNetMall("1000001","*******");
        String base64 = netMall.generateGoodsPoster("https://item.jd.com/100008348542.html");
        logger.info("测试结果：{}", base64);
    }

}
