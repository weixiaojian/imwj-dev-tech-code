package com.imwj.big.market.domain.service.rule.tree.impl;

import com.imwj.big.market.domain.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.service.rule.tree.ILogicTreeNode;
import com.imwj.big.market.domain.service.rule.tree.factory.DefaultTreeFactory;
import com.imwj.big.market.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wj
 * @create 2024-05-09 17:41
 * @description 兜底奖励节点
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {


    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则TreeNode-兜底奖励节点 userId:{} strategyId:{} awardId:{} ruleValue:{}", userId, strategyId, awardId, ruleValue);
        // 将数据库中配置的默认奖品返回ruleValue=101:1,100
        String[] split = ruleValue.split(Constants.COLON);
        if(split.length == 0){
            log.error("规则TreeNode-兜底奖励节点，兜底奖品未配置 userId:{} strategyId:{} awardId:{} ruleValue:{}", userId, strategyId, awardId, ruleValue);
            throw new RuntimeException("兜底奖品未配置：" + ruleValue);
        }
        // 解析兜底奖品及其规则
        Integer luckAwardId = Integer.valueOf(split[0]);
        String awardRuleValue = split.length > 1 ? split[1] : "";

        // 兜底奖励默认拦截：返回奖品id 101,ruleValue 1,100
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                        .awardId(luckAwardId)
                        .awardRuleValue(awardRuleValue)
                        .build())
                .build();
    }
}
