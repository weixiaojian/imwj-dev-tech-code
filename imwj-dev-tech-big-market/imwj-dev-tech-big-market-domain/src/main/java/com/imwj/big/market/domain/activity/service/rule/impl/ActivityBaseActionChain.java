package com.imwj.big.market.domain.activity.service.rule.impl;

import com.imwj.big.market.domain.activity.model.entity.ActivityCountEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivityEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivitySkuEntity;
import com.imwj.big.market.domain.activity.service.rule.AbstractActionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wj
 * @create 2024-08-28 17:25
 * @description 活动规则过滤【日期、状态】
 */
@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {

        log.info("活动责任链-基础信息【有效期、状态】校验开始。");

        return next().action(activitySkuEntity, activityEntity, activityCountEntity);
    }
}
