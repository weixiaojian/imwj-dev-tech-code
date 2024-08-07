package com.imwj.big.market.domain.strategy.service.rule.tree.factory.engine;

import com.imwj.big.market.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author wj
 * @create 2024-05-09 17:47
 * @description 规则树组合接口
 */
public interface IDecisionTreeEngine {

    /**
     * 决策树引擎处理接口
     * @param userId
     * @param strategyId
     * @param awardId
     */
    DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId);
}
