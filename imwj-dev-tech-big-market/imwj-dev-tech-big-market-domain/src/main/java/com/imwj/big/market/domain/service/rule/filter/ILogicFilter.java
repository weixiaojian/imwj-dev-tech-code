package com.imwj.big.market.domain.service.rule.filter;

import com.imwj.big.market.domain.model.entity.RuleActionEntity;
import com.imwj.big.market.domain.model.entity.RuleMatterEntity;

/**
 * @author wj
 * @create 2024-04-25 17:41
 * @description  规则过滤接口
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {

    /**
     * 抽奖过滤
     * @param ruleMatterEntity
     * @return
     */
    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);

}
