package com.imwj.big.market.domain.service.rule.impl;

import com.imwj.big.market.domain.model.entity.RaffleAwardEntity;
import com.imwj.big.market.domain.model.entity.RaffleFactorEntity;
import com.imwj.big.market.domain.model.entity.RuleActionEntity;
import com.imwj.big.market.domain.model.entity.RuleMatterEntity;
import com.imwj.big.market.domain.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.repository.IStrategyRepository;
import com.imwj.big.market.domain.service.IRaffleStrategy;
import com.imwj.big.market.domain.service.annotaion.LogicStrategy;
import com.imwj.big.market.domain.service.rule.ILogicFilter;
import com.imwj.big.market.domain.service.rule.factory.DefaultLogicFactory;
import com.imwj.big.market.types.common.Constants;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-04-25 17:42
 * @description 黑名单过滤器
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_BLACKLIST)
public class RuleBackListLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {

    @Resource
    private IStrategyRepository strategyRepository;

    /**
     * 黑名单规则过滤：
     * 1.传入用户userId、策略strategyId、抽奖规则ruleModel
     * 2.查询出数据库中的strategy_rule表数据
     * 3.根据用户id做比对 用户id相同则需要拦截（生成RuleActionEntity规则动作实体）
     * @param ruleMatterEntity
     * @return
     */
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-黑名单 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(),
                ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());
        String userId = ruleMatterEntity.getUserId();

        // 1.查询strategy_rule表中的rule_value（100:user1,user2,user3）
        String ruleValue = strategyRepository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(),
                ruleMatterEntity.getRuleModel());

        // 2.根据字符串拆分得到奖品id(100)和用户id(user1,user2,user3)
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.valueOf(splitRuleValue[0]);
        String[] userBlankIds = splitRuleValue[1].split(Constants.SPLIT);
        for(String userBlankId : userBlankIds){
            // 用户id和黑名单中的相等：则需要拦截接管
            if(userId.equals(userBlankId)){
                return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                        .ruleModel(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode())
                        .data(RuleActionEntity.RaffleBeforeEntity.builder()
                                .strategyId(ruleMatterEntity.getStrategyId())
                                .awardId(awardId)
                                .build())
                        .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                        .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                        .build();
            }
        }
        // 3.用户id没在黑名单中直接放行
        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }
}
