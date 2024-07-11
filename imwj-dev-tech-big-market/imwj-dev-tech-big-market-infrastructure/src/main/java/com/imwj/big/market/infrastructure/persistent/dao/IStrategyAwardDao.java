package com.imwj.big.market.infrastructure.persistent.dao;

import com.imwj.big.market.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wj
 * @create 2024-04-18 17:25
 * @description 抽奖策略奖品明细配置
 */
@Mapper
public interface IStrategyAwardDao {

    /**
     * 根据策略id查询策略集合
     * @param strategyId
     * @return
     */
    List<StrategyAward> queryStrategyAwardList(Long strategyId);

    /**
     * 根据策略id和奖品id查询策略规则role_models
     * @param strategyAward
     * @return
     */
    String queryStrategyAwardRuleModels(StrategyAward strategyAward);

    /**
     * 更新数据库中的商品表库存
     * @param strategyAward
     */
    void updateStrategyAwardStock(StrategyAward strategyAward);

    /**
     * 根据策略id和奖品id查询奖品数据
     * @param strategyId
     * @param awardId
     * @return
     */
    StrategyAward queryStrategyAward(@Param("strategyId") Long strategyId, @Param("awardId")Integer awardId);
}
