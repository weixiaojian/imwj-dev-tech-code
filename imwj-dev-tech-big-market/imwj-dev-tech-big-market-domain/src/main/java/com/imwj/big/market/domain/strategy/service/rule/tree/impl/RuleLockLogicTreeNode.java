package com.imwj.big.market.domain.strategy.service.rule.tree.impl;

import com.imwj.big.market.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.imwj.big.market.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wj
 * @create 2024-05-09 17:40
 * @description 次数锁节点（用户抽奖次数需要大于规定的次数）
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {

    // 用户抽奖次数（此处写个默认值 一般从数据库/redis中获取）
    private Long userRaffleCount = 0L;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则TreeNode-次数锁节点 userId:{} strategyId:{} awardId:{} ruleValue:{}", userId, strategyId, awardId, ruleValue);

        // 数据库中配置的用户次数限制
        long raffleCount = Long.valueOf(ruleValue);
        // 如果用户抽奖次数大于数据库中的次数（满足条件-放行）
        if(userRaffleCount >= raffleCount){
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                    .build();
        }
        // 如果用户抽奖次数小于数据库中的次数（不满足条件-拦截）
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }
}
