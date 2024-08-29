package com.imwj.big.market.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.imwj.big.market.domain.activity.model.entity.ActivityShopCartEntity;
import com.imwj.big.market.domain.activity.model.entity.SkuRechargeEntity;
import com.imwj.big.market.domain.activity.service.IRaffleOrder;
import com.imwj.big.market.domain.activity.service.RaffleActivityService;
import com.imwj.big.market.infrastructure.persistent.dao.IRaffleActivityOrderDao;
import com.imwj.big.market.infrastructure.persistent.po.RaffleActivityOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wj
 * @create 2024-08-12 17:16
 * @description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityOrderDaoTest {

    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;

    @Resource
    private RaffleActivityService raffleActivityService;

    @Resource
    private IRaffleOrder raffleOrder;

    @Test
    public void test_insert() {
        RaffleActivityOrder raffleActivityOrder = new RaffleActivityOrder();
        raffleActivityOrder.setUserId("imwj");
        raffleActivityOrder.setSku(10001L);
        raffleActivityOrder.setActivityId(100301L);
        raffleActivityOrder.setActivityName("测试活动");
        raffleActivityOrder.setStrategyId(100006L);
        raffleActivityOrder.setOrderId(RandomStringUtils.randomNumeric(12));
        raffleActivityOrder.setOrderTime(new Date());
        raffleActivityOrder.setTotalCount(100L);
        raffleActivityOrder.setDayCount(50L);
        raffleActivityOrder.setMonthCount(10L);
        raffleActivityOrder.setState("not_used");
        raffleActivityOrder.setOutBusinessNo("89128938");
        // 插入数据
        raffleActivityOrderDao.insert(raffleActivityOrder);
    }

    @Test
    public void test_queryRaffleActivityOrderByUserId() {
        String userId = "imwj";
        List<RaffleActivityOrder> raffleActivityOrders = raffleActivityOrderDao.queryRaffleActivityOrderByUserId(userId);
        log.info("测试结果：{}", JSON.toJSONString(raffleActivityOrders));
    }

    @Test
    public void test_createRaffleActivityOrder() {
        ActivityShopCartEntity activityShopCartEntity = new ActivityShopCartEntity();
        activityShopCartEntity.setSku(9011L);
        activityShopCartEntity.setUserId("imwj");
        raffleActivityService.queryRaffleActivityOrder(activityShopCartEntity);
    }

    @Test
    public void test_createSkuRechargeOrder() {
        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("imwj");
        skuRechargeEntity.setSku(9011L);
        // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突 Duplicate entry '700091009111' for key 'uq_out_business_no' 确保唯一性。
        skuRechargeEntity.setOutBusinessNo("700091009111");
        String orderId = raffleOrder.createSkuRechargeOrder(skuRechargeEntity);
        log.info("测试结果：{}", orderId);
    }
}