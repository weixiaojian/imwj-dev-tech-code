package com.imwj.big.market.domain.service.rule.chatin.impl;

import com.imwj.big.market.domain.model.entity.RuleActionEntity;
import com.imwj.big.market.domain.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.repository.IStrategyRepository;
import com.imwj.big.market.domain.service.rule.chatin.AbstractLongChain;
import com.imwj.big.market.domain.service.rule.chatin.factory.DefaultChainFactory;
import com.imwj.big.market.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-05-08 17:12
 * @description 黑名单责任链
 */
@Slf4j
@Component("rule_blacklist")
public class RuleBackListLogicChain extends AbstractLongChain {

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("责任链-规则过滤-黑名单 userId:{} strategyId:{} ruleModel:{}", userId, strategyId);

        // 1.查询strategy_rule表中的rule_value（100:user1,user2,user3）
        String ruleValue = strategyRepository.queryStrategyRuleValue(strategyId, ruleModel());
        // 2.根据字符串拆分得到奖品id(100)和用户id(user1,user2,user3)
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.valueOf(splitRuleValue[0]);
        String[] userBlankIds = splitRuleValue[1].split(Constants.SPLIT);
        for(String userBlankId : userBlankIds){
            // 用户id和黑名单中的相等：则需要拦截接管 直接返回奖品id
            if(userId.equals(userBlankId)){
                log.info("抽奖责任链-黑名单接管 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
                return DefaultChainFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .logicModel(ruleValue)
                        .build();
            }
        }
        // 3.用户id没在黑名单中 进入下一个责任链
        log.info("抽奖责任链-黑名单放行 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_BLACKLIST.getCode();
    }
}
