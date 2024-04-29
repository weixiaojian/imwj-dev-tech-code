package com.imwj.big.market.domain.repository;

import com.imwj.big.market.domain.model.entity.StrategyAwardEntity;
import com.imwj.big.market.domain.model.entity.StrategyEntity;
import com.imwj.big.market.domain.model.entity.StrategyRuleEntity;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @create 2024-04-24 16:54
 * @description 策略仓储数据库接口
 */
public interface IStrategyRepository {

    /**
     * 查询策略奖品list
     * @param strategyId
     * @return
     */
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    /**
     * 存储策略奖励map
     * @param strategyId
     * @param rateRange
     * @param strategyAwardSearchRateTable
     */
    void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);

    /**
     * 从存储策略奖励map中获取奖品
     * @param strategyId
     * @param rateKey
     * @return
     */
    Integer getStrategyAwardAssemble(String key, Integer rateKey);

    /**
     * 获取中奖率范围
     * @param strategyId
     * @return
     */
    int getRateRange(String strategyId);

    /**
     * 查询策略实体（充血）
     * @param strategyId
     * @return
     */
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    /**
     * 查询策略规则实体（充血）
     * @param strategyId
     * @param ruleModel
     * @return
     */
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);
}
