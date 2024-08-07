package com.imwj.big.market.domain.strategy.service.armory;

/**
 * @author wj
 * @create 2024-04-24 16:55
 * @description 装配工厂接口
 */
public interface IStrategyArmory {

    /**
     * 装配抽奖策略配置「触发的时机可以为活动审核通过后进行调用」
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    boolean assembleLotteryStrategy(Long strategyId);
}
