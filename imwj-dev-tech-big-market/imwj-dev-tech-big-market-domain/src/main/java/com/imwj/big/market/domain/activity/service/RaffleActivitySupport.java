package com.imwj.big.market.domain.activity.service;

import com.imwj.big.market.domain.activity.model.entity.ActivityCountEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivityEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivitySkuEntity;
import com.imwj.big.market.domain.activity.repository.IActivityRepository;
import com.imwj.big.market.domain.activity.service.rule.factory.DefaultActivityChainFactory;

/**
 * @author wj
 * @create 2024-08-28 17:21
 * @description 抽奖活动支持类（简化抽象类）
 */
public class RaffleActivitySupport {

    protected DefaultActivityChainFactory defaultActivityChainFactory;

    protected IActivityRepository activityRepository;

    public RaffleActivitySupport(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        this.activityRepository = activityRepository;
        this.defaultActivityChainFactory = defaultActivityChainFactory;
    }

    /**
     * 查询sku数据
     * @param sku
     * @return
     */
    public ActivitySkuEntity queryActivitySku(Long sku){
        return activityRepository.queryActivitySku(sku);
    }


    /**
     * 查询活动数据
     * @param activityId
     * @return
     */
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        return activityRepository.queryRaffleActivityByActivityId(activityId);
    }

    /**
     * 查询活动次数数据
     * @param activityCountId
     * @return
     */
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }
}
