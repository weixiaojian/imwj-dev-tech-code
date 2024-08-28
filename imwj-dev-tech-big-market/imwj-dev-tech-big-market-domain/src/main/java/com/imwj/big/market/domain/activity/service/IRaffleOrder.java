package com.imwj.big.market.domain.activity.service;

import com.imwj.big.market.domain.activity.model.entity.ActivityOrderEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivityShopCartEntity;

/**
 * @author wj
 * @create 2024-08-28 15:05
 * @description 抽奖活动订单接口
 */
public interface IRaffleOrder {

    /**
     * 以sku创建抽奖活动订单，获得参与抽奖资格（可消耗的次数）
     *
     * @param activityShopCartEntity 活动sku实体，通过sku领取活动。
     * @return 活动参与记录实体
     */
    ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity);

}
