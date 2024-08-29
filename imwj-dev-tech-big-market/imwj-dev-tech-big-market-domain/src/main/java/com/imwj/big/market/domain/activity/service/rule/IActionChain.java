package com.imwj.big.market.domain.activity.service.rule;

import com.imwj.big.market.domain.activity.model.entity.ActivityCountEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivityEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author wj
 * @create 2024-08-28 17:24
 * @description 下单规则过滤接口
 */
public interface IActionChain extends IActionChainArmory{

    /**
     * 执行抽奖活动操作
     * @param activitySkuEntity
     * @param activityEntity
     * @param activityCountEntity
     * @return
     */
    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

}
