package com.imwj.big.market.domain.service.rule.chatin.impl;

import com.imwj.big.market.domain.repository.IStrategyRepository;
import com.imwj.big.market.domain.service.armory.IStrategyDispatch;
import com.imwj.big.market.domain.service.rule.chatin.AbstractLongChain;
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
@Component("default")
public class DefaultLogicChain extends AbstractLongChain {

    @Resource
    protected IStrategyDispatch strategyDispatch;

    @Override
    public Integer logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("抽奖责任链-默认处理 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
        return awardId;
    }


    @Override
    protected String ruleModel() {
        return "default";
    }
}
