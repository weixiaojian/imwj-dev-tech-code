package com.imwj.big.market.domain.activity.repository;

import com.imwj.big.market.domain.activity.model.entity.ActivityCountEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivityEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author wj
 * @create 2024-08-28 15:05
 * @description 活动仓储接口
 */
public interface IActivityRepository {

    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

}
