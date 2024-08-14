package com.imwj.big.market.domain.strategy.service.rule.chatin.impl;

import com.imwj.big.market.domain.strategy.repository.IStrategyRepository;
import com.imwj.big.market.domain.strategy.service.rule.chatin.factory.DefaultChainFactory;
import com.imwj.big.market.domain.strategy.service.armory.IStrategyDispatch;
import com.imwj.big.market.domain.strategy.service.rule.chatin.AbstractLongChain;
import com.imwj.big.market.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wj
 * @create 2024-05-08 17:15
 * @description 权重责任链
 */
@Slf4j
@Component("rule_weight")
public class RuleWeightLogicChain extends AbstractLongChain {

    @Resource
    private IStrategyRepository strategyRepository;
    @Resource
    protected IStrategyDispatch strategyDispatch;

    /*虚拟用户积分*/
    private Long userScore = 0L;


    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("责任链-规则过滤-权重 userId:{} strategyId:{} ruleModel:{}", userId, strategyId);
        // 1.查询strategy_rule表中的rule_value（4000:102,103,104,105 5000:102,103,104,105,106,107 6000:107,108,109）
        String ruleValue = strategyRepository.queryStrategyRuleValue(strategyId, ruleModel());
        // 2.根据字符串拆分得到权重Map<4000, 4000:102,103,104,105>
        Map<Long, String> ruleValueMap = getAnalyticalValue(ruleValue);
        // 没有设定规则 > 直接放行
        if(ruleValueMap == null || ruleValueMap.isEmpty()){
            return null;
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
        // 5.有符合权重的积分值
        if(fisrtKey != null){
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, ruleValueMap.get(fisrtKey));
            log.info("抽奖责任链权重接管 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
            return DefaultChainFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .logicModel(ruleValue)
                    .build();
        }
        log.info("抽奖责任链-权重放行 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode();
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
