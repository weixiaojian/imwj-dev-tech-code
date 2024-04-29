package com.imwj.big.market.domain.service.rule.factory;

import com.alibaba.fastjson2.util.AnnotationUtils;
import com.imwj.big.market.domain.model.entity.RuleActionEntity;
import com.imwj.big.market.domain.service.annotaion.LogicStrategy;
import com.imwj.big.market.domain.service.rule.ILogicFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wj
 * @create 2024-04-25 17:44
 * @description 默认的规则工厂
 */
@Service
public class DefaultLogicFactory {

    /*存储规则map*/
    public Map<String, ILogicFilter> logicFilterMap = new ConcurrentHashMap<>();

    /**
     * 构造方法：传入策略拦截规则集合 根据自定义注解@LogicStrategy标识不同规则类 并放入logicFilterMap
     * @param logicFilters
     */
    public DefaultLogicFactory(List<ILogicFilter<?>> logicFilters){
        logicFilters.forEach(logic -> {
            // 找到所有标识为策略拦截规则的类
            LogicStrategy strategy = AnnotationUtils.findAnnotation(logic.getClass(), LogicStrategy.class);
            logicFilterMap.put(strategy.logicMode().getCode(), logic);
        });
    }

    /**
     * 获取logicFilterMap
     * @return
     * @param <T>
     */
    public <T extends RuleActionEntity.RaffleEntity> Map<String, ILogicFilter<T>> openLogicFilter(){
        return (Map<String, ILogicFilter<T>>) (Map<?, ?>)logicFilterMap;
    }


    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_WIGHT("rule_weight","【抽奖前规则】根据抽奖权重返回可抽奖范围KEY"),
        RULE_BLACKLIST("rule_blacklist","【抽奖前规则】黑名单规则过滤，命中黑名单则直接返回"),
        ;

        private final String code;
        private final String info;
    }
}
