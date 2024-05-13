package com.imwj.big.market.domain.service.rule.chatin.impl;

import com.imwj.big.market.domain.repository.IStrategyRepository;
import com.imwj.big.market.domain.service.armory.IStrategyDispatch;
import com.imwj.big.market.domain.service.rule.chatin.AbstractLongChain;
import com.imwj.big.market.domain.service.rule.chatin.factory.DefaultChainFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-05-08 17:18
 * @description 默认的责任链节点（兜底）
 */
@Slf4j
@Component("rule_default")
public class DefaultLogicChain extends AbstractLongChain {

    @Resource
    protected IStrategyDispatch strategyDispatch;

    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("抽奖责任链-默认处理 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
        return DefaultChainFactory.StrategyAwardVO.builder()
                .awardId(awardId)
                .logicModel(ruleModel())
                .build();
    }


    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode();
    }
}
