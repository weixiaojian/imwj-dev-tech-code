package com.imwj.big.market.infrastructure.persistent.repository;


import com.imwj.big.market.domain.activity.model.entity.ActivityCountEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivityEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivitySkuEntity;
import com.imwj.big.market.domain.activity.model.valobj.ActivityStateVO;
import com.imwj.big.market.domain.activity.repository.IActivityRepository;
import com.imwj.big.market.infrastructure.persistent.dao.IRaffleActivityCountDao;
import com.imwj.big.market.infrastructure.persistent.dao.IRaffleActivityDao;
import com.imwj.big.market.infrastructure.persistent.dao.IRaffleActivitySkuDao;
import com.imwj.big.market.infrastructure.persistent.po.RaffleActivity;
import com.imwj.big.market.infrastructure.persistent.po.RaffleActivityCount;
import com.imwj.big.market.infrastructure.persistent.po.RaffleActivitySku;
import com.imwj.big.market.infrastructure.persistent.redis.IRedisService;
import com.imwj.big.market.types.common.Constants;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-08-28 15:10
 * @description
 */
@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IRedisService redisService;
    @Resource
    private IRaffleActivityDao raffleActivityDao;
    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;
    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;

    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(sku);
        return ActivitySkuEntity.builder()
                .sku(raffleActivitySku.getSku())
                .activityId(raffleActivitySku.getActivityId())
                .activityCountId(raffleActivitySku.getActivityCountId())
                .stockCount(raffleActivitySku.getStockCount())
                .stockCountSurplus(raffleActivitySku.getStockCountSurplus())
                .build();
    }

    @Override
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        ActivityEntity activityEntity = new ActivityEntity();
        // 优先从缓存获取
        /*String cacheKey = Constants.RedisKey.ACTIVITY_KEY + activityId;
        activityEntity = redisService.getValue(cacheKey);
        if (null != activityEntity) return activityEntity;*/
        // 从库中获取数据
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(activityId);
        activityEntity = ActivityEntity.builder()
                .activityId(raffleActivity.getActivityId())
                .activityName(raffleActivity.getActivityName())
                .activityDesc(raffleActivity.getActivityDesc())
                .beginDateTime(raffleActivity.getBeginDateTime())
                .endDateTime(raffleActivity.getEndDateTime())
                .strategyId(raffleActivity.getStrategyId())
                .state(ActivityStateVO.valueOf(raffleActivity.getState()))
                .build();
        // redisService.setValue(cacheKey, activityEntity);
        return activityEntity;
    }

    @Override
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        ActivityCountEntity activityCountEntity = new ActivityCountEntity();
        // 优先从缓存获取
        /*String cacheKey = Constants.RedisKey.ACTIVITY_COUNT_KEY + activityCountId;
        ActivityCountEntity activityCountEntity = redisService.getValue(cacheKey);
        if (null != activityCountEntity) return activityCountEntity;*/
        // 从库中获取数据
        RaffleActivityCount raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(activityCountId);
        activityCountEntity = ActivityCountEntity.builder()
                .activityCountId(raffleActivityCount.getActivityCountId())
                .totalCount(raffleActivityCount.getTotalCount())
                .dayCount(raffleActivityCount.getDayCount())
                .monthCount(raffleActivityCount.getMonthCount())
                .build();
        // redisService.setValue(cacheKey, activityCountEntity);
        return activityCountEntity;
    }

}
