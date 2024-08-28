package com.imwj.big.market.domain.activity.service;

import com.alibaba.fastjson.JSON;
import com.imwj.big.market.domain.activity.model.entity.*;
import com.imwj.big.market.domain.activity.repository.IActivityRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wj
 * @create 2024-08-28 15:05
 * @description 抽奖活动抽象类
 */
@Slf4j
public abstract class AbstractRaffleActivity implements IRaffleOrder {

    protected IActivityRepository activityRepository;

    public AbstractRaffleActivity(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity) {
        // 1. 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(activityShopCartEntity.getSku());
        // 2. 查询活动信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 3. 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        log.info("查询结果：{} {} {}", JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityEntity), JSON.toJSONString(activityCountEntity));

        return ActivityOrderEntity.builder().build();
    }

}