package com.imwj.big.market.domain.service.rule.chatin;

/**
 * @author wj
 * @create 2024-05-07 17:18
 * @description 抽奖规则责任链接口
 */
public interface ILogicChain extends ILogicChainArmory{

    /**
     * 责任链接口
     * @param userId
     * @param strategyId
     * @return
     */
    Integer logic(String userId, Long strategyId);

}
