package com.imwj.big.market.domain.strategy.service.rule.tree.impl;

import com.imwj.big.market.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.strategy.model.valobj.StrategyAwardStockKeyVo;
import com.imwj.big.market.domain.strategy.repository.IStrategyRepository;
import com.imwj.big.market.domain.strategy.service.armory.IStrategyDispatch;
import com.imwj.big.market.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.imwj.big.market.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-05-09 17:42
 * @description 库存扣减节点
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {

    @Resource
    private IStrategyDispatch strategyDispatch;
    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则TreeNode-库存扣减节点 userId:{} strategyId:{} awardId:{} ruleValue:{}", userId, strategyId, awardId, ruleValue);
        // 库存扣减（true扣减成功 false扣减失败）
        Boolean status = strategyDispatch.subtractionAwardStock(strategyId, awardId);
        if(status){
            // 写入延迟消息队列  延迟消费更新数据库记录（通过UpdateAwardStockJob定时任务去更新数据库记录）
            strategyRepository.awardStockConsumeSendQueue(StrategyAwardStockKeyVo.builder()
                    .strategyId(strategyId)
                    .awardId(awardId)
                    .build());
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                    .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                            .awardId(awardId)
                            .awardRuleValue(ruleValue)
                            .build())
                    .build();
        }
        // 库存扣减失败放行（没有奖品）
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }
}
