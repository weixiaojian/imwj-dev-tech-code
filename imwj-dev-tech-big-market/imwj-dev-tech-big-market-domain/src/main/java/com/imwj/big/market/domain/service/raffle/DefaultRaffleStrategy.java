package com.imwj.big.market.domain.service.raffle;

import com.imwj.big.market.domain.model.entity.RaffleFactorEntity;
import com.imwj.big.market.domain.model.entity.RuleActionEntity;
import com.imwj.big.market.domain.model.entity.RuleMatterEntity;
import com.imwj.big.market.domain.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.repository.IStrategyRepository;
import com.imwj.big.market.domain.service.AbstractRaffleStrategy;
import com.imwj.big.market.domain.service.armory.IStrategyDispatch;
import com.imwj.big.market.domain.service.rule.filter.ILogicFilter;
import com.imwj.big.market.domain.service.rule.chatin.factory.DefaultChainFactory;
import com.imwj.big.market.domain.service.rule.filter.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author wj
 * @create 2024-04-25 17:38
 * @description 默认的抽奖策略实现
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {

    @Resource
    private DefaultLogicFactory logicFactory;

    public DefaultRaffleStrategy(IStrategyRepository strategyRepository, IStrategyDispatch strategyDispatch
            , DefaultChainFactory defaultChainFactory) {
        super(strategyRepository, strategyDispatch, defaultChainFactory);
    }


    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        // 没有参数传入 直接放行
        if(logics == null || logics.length == 0){
            return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        // 1。获取所有的规则过滤map
        Map<String, ILogicFilter<RuleActionEntity.RaffleCenterEntity>> logicFilterMap = logicFactory.openLogicFilter();

        // 2.循环过滤规则
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionEntity = null;
        for(String  ruleModel : logics){
            ILogicFilter<RuleActionEntity.RaffleCenterEntity> logicFilter = logicFilterMap.get(ruleModel);
            RuleMatterEntity ruleMatterEntity = new RuleMatterEntity();
            ruleMatterEntity.setUserId(raffleFactorEntity.getUserId());
            ruleMatterEntity.setAwardId(raffleFactorEntity.getAwardId());
            ruleMatterEntity.setStrategyId(raffleFactorEntity.getStrategyId());
            ruleMatterEntity.setRuleModel(ruleModel);
            ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            // 有拦截规则就返回
            log.info("抽奖中规则过滤 userId: {} ruleModel: {} code: {} info: {}", raffleFactorEntity.getUserId(), ruleModel, ruleActionEntity.getCode(), ruleActionEntity.getInfo());
            if(!RuleLogicCheckTypeVO.ALLOW.getCode().equals(ruleActionEntity.getCode())){
                return ruleActionEntity;
            }
        }
        // 3.没有匹配到规则 返回空或者放行
        return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }
}
