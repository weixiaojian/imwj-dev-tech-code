package com.imwj.big.market.domain.service.rule.tree.impl;

import com.imwj.big.market.domain.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.service.rule.tree.ILogicTreeNode;
import com.imwj.big.market.domain.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wj
 * @create 2024-05-09 17:42
 * @description 库存扣减节点
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        log.info("库存扣减节点 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);

        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }
}
