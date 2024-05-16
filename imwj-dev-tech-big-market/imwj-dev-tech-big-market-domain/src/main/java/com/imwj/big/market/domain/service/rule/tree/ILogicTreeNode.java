package com.imwj.big.market.domain.service.rule.tree;

import com.imwj.big.market.domain.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author wj
 * @create 2024-05-09 17:38
 * @description 规则树接口
 */
public interface ILogicTreeNode {

    /**
     * 规则树逻辑处理
     * @param userId
     * @param strategyId
     * @param awardId
     * @param ruleValue
     */
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue);

}
