package com.imwj.big.market.domain.service.rule.impl;

import com.imwj.big.market.domain.model.entity.RuleActionEntity;
import com.imwj.big.market.domain.model.entity.RuleMatterEntity;
import com.imwj.big.market.domain.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.repository.IStrategyRepository;
import com.imwj.big.market.domain.service.annotaion.LogicStrategy;
import com.imwj.big.market.domain.service.rule.ILogicFilter;
import com.imwj.big.market.domain.service.rule.factory.DefaultLogicFactory;
import com.imwj.big.market.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.Key;
import java.util.*;

/**
 * @author wj
 * @create 2024-04-25 17:42
 * @description 权重过滤器
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_WIGHT)
public class RuleWeightLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {

    @Resource
    private IStrategyRepository strategyRepository;

    /*虚拟用户积分*/
    private Long userScore = 6500L;


    /**
     * 权重规则过滤：
     * 1.策略strategyId、抽奖规则ruleModel、用户积分userScore
     * 2.查询出数据库中的strategy_rule表数据
     * 3.根据积分做比对，复核规则的则需要拦截（生成RuleActionEntity规则动作实体）
     * @param ruleMatterEntity
     * @return
     */
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-权重 userScore:{} strategyId:{} ruleModel:{}", userScore,
                ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());

        // 1.查询strategy_rule表中的rule_value（4000:102,103,104,105 5000:102,103,104,105,106,107 6000:107,108,109）
        String ruleValue = strategyRepository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(),
                ruleMatterEntity.getRuleModel());
        // 2.根据字符串拆分得到权重Map<4000, 4000:102,103,104,105>
        Map<Long, String> ruleValueMap = getAnalyticalValue(ruleValue);
        // 没有设定规则 > 直接放行
        if(ruleValueMap == null || ruleValueMap.isEmpty()){
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }
        // 3.针对map中的key(积分值)排序
        ArrayList<Long> scoreList = new ArrayList<>(ruleValueMap.keySet());
        Collections.sort(scoreList);

        // 4.找出最后一个符合值（4500积分>4000 5500积分>5000）
        Long fisrtKey = scoreList.stream()
                .sorted(Comparator.reverseOrder())
                .filter(analyticalSortedKeyValue -> userScore >= analyticalSortedKeyValue)
                .findFirst()
                .orElse(null);

        // 5.有符合的积分那么我们就开始返回
        String ruleValueKey = ruleValueMap.get(fisrtKey);
        if(StringUtils.isNotBlank(ruleValueKey)){
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .ruleModel(DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode())
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(ruleMatterEntity.getStrategyId())
                            .ruleWeightValueKey(ruleValueKey)
                            .build())
                    .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                    .build();
        }
        
        // 直接放行
        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }

    /**
     * 降规制值转换为map对象
     * （4000:102,103,104,105 5000:102,103,104,105,106,107 6000:107,108,109） > <4000, "4000:102,103,104,105 ">
     * @return
     */
    private Map<Long, String> getAnalyticalValue(String ruleValue){
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long, String> ruleValueMap = new HashMap<>();
        for(String ruleValueKey :  ruleValueGroups){
            if(StringUtils.isBlank(ruleValueKey)){
                return null;
            }
            // 分割字符串
            String[] parts = ruleValueKey.split(Constants.COLON);
            if(parts.length != 2){
                throw new IllegalArgumentException("ruleValue长度不正常！");
            }
            ruleValueMap.put(Long.valueOf(parts[0]), ruleValueKey);
        }
        return ruleValueMap;
    }
}
