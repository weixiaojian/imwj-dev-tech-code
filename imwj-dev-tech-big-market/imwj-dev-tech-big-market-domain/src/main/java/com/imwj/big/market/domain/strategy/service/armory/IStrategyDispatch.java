package com.imwj.big.market.domain.strategy.service.armory;

/**
 * @author wj
 * @create 2024-04-25 14:16
 * @description 策略抽奖调度
 */
public interface IStrategyDispatch {

    /**
     * 获取抽奖策略装配的随机结果
     *
     * @param strategyId 策略ID
     * @return 抽奖结果
     */
    Integer getRandomAwardId(Long strategyId);

    /**
     * 获取抽奖策略装配的随机结果（带权重）
     * @param strategyId
     * @param ruleWeightValue
     * @return
     */
    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

    /**
     * 获取抽奖策略装配的随机结果
     * @param key
     * @return
     */
    Integer getRandomAwardId(String key);

    /**
     * 扣减库存（redis）
     * @param strategyId
     * @param awardId
     * @return
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId);
}
