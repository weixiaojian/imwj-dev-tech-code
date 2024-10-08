package com.imwj.big.market.domain.strategy.service.rule.tree.factory;

import com.imwj.big.market.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.strategy.service.rule.tree.factory.engine.impl.DescisionTreeEngine;
import com.imwj.big.market.domain.strategy.model.valobj.RuleTreeVO;
import com.imwj.big.market.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.imwj.big.market.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wj
 * @create 2024-05-09 17:45
 * @description 默认的树组装工厂
 */
@Service
public class DefaultTreeFactory {

    private final Map<String, ILogicTreeNode> logicTreeNodeMap;

    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeMap) {
        this.logicTreeNodeMap = logicTreeNodeMap;
    }

    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO){
        return new DescisionTreeEngine(logicTreeNodeMap, ruleTreeVO);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity{
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardVO strategyAwardVO;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardData{
        /*抽奖奖品ID*/
        private Integer awardId;
        /*抽奖奖品规则*/
        private String awardRuleValue;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /** 抽奖奖品规则 */
        private String awardRuleValue;
    }

}
