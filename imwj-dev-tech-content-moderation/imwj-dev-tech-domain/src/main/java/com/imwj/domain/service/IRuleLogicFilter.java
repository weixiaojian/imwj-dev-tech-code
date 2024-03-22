package com.imwj.domain.service;

import com.imwj.domain.model.entity.RuleActionEntity;
import com.imwj.domain.model.entity.RuleMatterEntity;

/**
 * @author wj
 * @create 2024-03-22 16:31
 * @description 规则过滤：敏感词、内容审核
 */
public interface IRuleLogicFilter {

    RuleActionEntity<RuleMatterEntity> filter(RuleMatterEntity ruleMatterEntity);

}

