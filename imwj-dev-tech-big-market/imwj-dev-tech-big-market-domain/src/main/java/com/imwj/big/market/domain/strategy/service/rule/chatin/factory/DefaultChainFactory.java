package com.imwj.big.market.domain.strategy.service.rule.chatin.factory;

import com.imwj.big.market.domain.strategy.model.entity.StrategyEntity;
import com.imwj.big.market.domain.strategy.repository.IStrategyRepository;
import com.imwj.big.market.domain.strategy.service.rule.chatin.ILogicChain;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wj
 * @create 2024-05-08 17:42
 * @description 默认的责任链工厂
 */
@Service
public class DefaultChainFactory {

    private final Map<String, ILogicChain> logicChainMap;

    private IStrategyRepository strategyRepository;


    public DefaultChainFactory(Map<String, ILogicChain> logicChainMap, IStrategyRepository strategyRepository) {
        this.logicChainMap = logicChainMap;
        this.strategyRepository = strategyRepository;
    }

    /**
     * 开启责任链操作
     * @param strategyId
     * @return
     */
    public ILogicChain openLogicChain(Long strategyId){
        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategyEntity.ruleModels();
        // 1.数据库中没有配置策略规则（直接返回默认的责任链处理）
        if(ruleModels == null || ruleModels.length == 0){
            return logicChainMap.get(DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode());
        }

        // 2.根据顺序开始填充链式节点
        ILogicChain logicChain = logicChainMap.get(ruleModels[0]);
        ILogicChain current = logicChain;
        for(int i = 1; i < ruleModels.length; i++){
            ILogicChain nextChain = logicChainMap.get(ruleModels[i]);
            current = current.appendNext(nextChain);
        }
        current.appendNext(logicChainMap.get(DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode()));
        return logicChain;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /** 抽奖规则 */
        private String logicModel;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }
}
