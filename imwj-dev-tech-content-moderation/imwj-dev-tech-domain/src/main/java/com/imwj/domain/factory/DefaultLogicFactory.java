package com.imwj.domain.factory;

import com.alibaba.fastjson2.util.AnnotationUtils;
import com.imwj.domain.annotation.LogicStrategy;
import com.imwj.domain.model.entity.RuleActionEntity;
import com.imwj.domain.model.entity.RuleMatterEntity;
import com.imwj.domain.model.vo.LogicCheckTypeVO;
import com.imwj.domain.service.IRuleLogicFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wj
 * @create 2024-03-22 16:38
 * @description 规则工厂
 */
@Service
public class DefaultLogicFactory {

    public Map<String, IRuleLogicFilter> logicFilterMap = new ConcurrentHashMap<>();

    public DefaultLogicFactory(List<IRuleLogicFilter> logicFilters){
        logicFilters.forEach(logic -> {
            LogicStrategy strategy = AnnotationUtils.findAnnotation(logic.getClass(), LogicStrategy.class);
            if(null != strategy){
                logicFilterMap.put(strategy.logicMode().code, logic);
            }
        });
    }

    public RuleActionEntity<RuleMatterEntity> doCheckLogic(RuleMatterEntity ruleMatterEntity, LogicModel... logics){
        RuleActionEntity<RuleMatterEntity> entity = null;
        for(LogicModel model : logics){
            entity = logicFilterMap.get(model.code).filter(ruleMatterEntity);
            if(!LogicCheckTypeVO.SUCCESS.equals(entity.getType())){
                return entity;
            }
            ruleMatterEntity = entity.getData();
        }
        return entity != null?entity:RuleActionEntity.<RuleMatterEntity>builder()
                .type(LogicCheckTypeVO.SUCCESS)
                .data(ruleMatterEntity)
                .build();
    }

    /**
     * 规则逻辑枚举
     */
    public enum LogicModel {

        SENSITIVE_WORD("SENSITIVE_WORD", "敏感词过滤"),
        CONTENT_SECURITY("CONTENT_SECURITY", "内容安全"),
        ;

        private String code;
        private String info;

        LogicModel(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
