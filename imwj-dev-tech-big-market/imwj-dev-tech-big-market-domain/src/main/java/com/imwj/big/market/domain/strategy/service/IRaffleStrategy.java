package com.imwj.big.market.domain.strategy.service;

import com.imwj.big.market.domain.strategy.model.entity.RaffleAwardEntity;
import com.imwj.big.market.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @author wj
 * @create 2024-04-25 17:27
 * @description 抽奖策略接口
 */
public interface IRaffleStrategy {

    /**
     * 执行抽奖操作
     * @param raffleFactorEntity 抽奖因子
     * @return 奖品实体
     */
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);

}
