package com.imwj.big.market.domain.repository;

import com.imwj.big.market.domain.model.entity.StrategyAwardEntity;

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
    void storeStrategyAwardSearchRateTable(Long strategyId, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);

    /**
     * 从存储策略奖励map中获取奖品
     * @param strategyId
     * @param rateKey
     * @return
     */
    Integer getStrategyAwardAssemble(Long strategyId, Integer rateKey);

    /**
     * 获取中奖率范围
     * @param strategyId
     * @return
     */
    int getRateRange(Long strategyId);
}
