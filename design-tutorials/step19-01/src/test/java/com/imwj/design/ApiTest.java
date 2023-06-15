package com.imwj.design;

import com.imwj.design.event.MJCouponDiscount;
import com.imwj.design.event.ZJCouponDiscount;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wj
 * @create 2023-06-15 17:50
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_zj() {
        Context<Double> context = new Context<Double>(new ZJCouponDiscount());
        BigDecimal discountAmount = context.discountAmount(10D, new BigDecimal(100));
        logger.info("测试结果：直减优惠后金额 {}", discountAmount);

        Context<Map<String, String>> context1 = new Context<Map<String, String>>(new MJCouponDiscount());
        HashMap<String, String> map = new HashMap<>();
        map.put("x", "100");
        map.put("n", "30");
        BigDecimal discountAmount1 = context1.discountAmount(map, new BigDecimal(100));
        logger.info("测试结果：满减优惠后金额 {}", discountAmount1);
    }
}
