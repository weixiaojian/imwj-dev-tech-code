package com.imwj.big.market.domain.strategy.repository;

import com.imwj.big.market.domain.strategy.model.entity.StrategyAwardEntity;
import com.imwj.big.market.domain.strategy.model.entity.StrategyEntity;
import com.imwj.big.market.domain.strategy.model.entity.StrategyRuleEntity;
import com.imwj.big.market.domain.strategy.model.valobj.RuleTreeVO;
import com.imwj.big.market.domain.strategy.model.valobj.StrategyAwardRuleModelVo;
import com.imwj.big.market.domain.strategy.model.valobj.StrategyAwardStockKeyVo;

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

    /**
     * 查询strategy_rule表中的rule_value
     * @param strategyId
     * @param awardId
     * @param ruleModel
     * @return
     */
    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);
    String queryStrategyRuleValue(Long strategyId, String ruleModel);

    /**
     * 根据策略id和奖品id获取到对应的规则rule_models
     * @param strategyId
     * @param awardId
     * @return
     */
    StrategyAwardRuleModelVo queryStrategyAwardRuleModel(Long strategyId, Integer awardId);

    /**
     * 查询数据库中的rule_tree、rule_tree_node、rule_tree_node_line数据并转换为Vo
     * @param treeId
     * @return
     */
    RuleTreeVO queryRuleTreeVoByTreeId(String treeId);

    /**
     * 奖品库存装配（redis）
     * @param cacheKey
     * @param awardCount
     */
    void cacheStrategyAwardCount(String cacheKey, Integer awardCount);

    /**
     * 扣减库存（redis）
     * @param cacheKey
     * @return
     */
    Boolean subtractionAwardStock(String cacheKey);

    /**
     * 写入延迟消息队列  延迟消费更新数据库记录（通过UpdateAwardStockJob定时任务去更新数据库记录）
     * @param build
     */
    void awardStockConsumeSendQueue(StrategyAwardStockKeyVo build);

    /**
     * 获取redis中的库存扣减队列
     * @return
     */
    StrategyAwardStockKeyVo takeQueueValue();

    /**
     * 更新数据库中的商品表库存
     * @param strategtId
     * @param awardId
     */
    void updateStrategyAwardStock(Long strategtId, Integer awardId);

    /**
     * 根据策略id和奖品id查询奖品实体
     * @param strategyId
     * @param awardId
     * @return
     */
    StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId);
}
