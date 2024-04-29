package com.imwj.big.market.infrastructure.persistent.dao;

import com.imwj.big.market.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wj
 * @create 2024-04-18 17:25
 * @description 策略规则 DAO
 */
@Mapper
public interface IStrategyRuleDao {

    /**
     * 查询所有的抽奖规则
     * @return
     */
    List<StrategyRule> queryStrategyRuleList();

    /**
     * 根据抽奖策略ID和规则类型查询
     * @param strategyRuleReq
     * @return
     */
    StrategyRule queryStrategyRule(StrategyRule strategyRuleReq);

    /**
     * 根据抽奖策略ID、规则类型、奖品ID查询抽奖规则值
     * @param strategyRuleReq
     * @return
     */
    String queryStrategyRuleValue(StrategyRule strategyRuleReq);

}
