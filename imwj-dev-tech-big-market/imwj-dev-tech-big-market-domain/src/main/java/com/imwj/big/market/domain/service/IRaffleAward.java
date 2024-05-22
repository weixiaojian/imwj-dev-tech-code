package com.imwj.big.market.domain.service;

import com.imwj.big.market.domain.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author wj
 * @create 2024-05-22 17:13
 * @description 策略奖品接口
 */
public interface IRaffleAward {


    /**
     * 根据策略id查询奖品列表
     * @param strategyId
     * @return
     */
    List<StrategyAwardEntity>  queryRaffleStrategyAwardList(Long strategyId);
}
