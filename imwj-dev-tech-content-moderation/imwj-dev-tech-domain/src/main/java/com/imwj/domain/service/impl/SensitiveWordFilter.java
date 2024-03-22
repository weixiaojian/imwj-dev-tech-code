package com.imwj.domain.service.impl;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.imwj.domain.annotation.LogicStrategy;
import com.imwj.domain.factory.DefaultLogicFactory;
import com.imwj.domain.model.entity.RuleActionEntity;
import com.imwj.domain.model.entity.RuleMatterEntity;
import com.imwj.domain.model.vo.LogicCheckTypeVO;
import com.imwj.domain.service.IRuleLogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-03-22 17:32
 * @description 敏感词
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.SENSITIVE_WORD)
public class SensitiveWordFilter implements IRuleLogicFilter {

    @Resource
    private SensitiveWordBs words;

    @Override
    public RuleActionEntity<RuleMatterEntity> filter(RuleMatterEntity ruleMatterEntity) {
        // 敏感词过滤
        String content = ruleMatterEntity.getContent();
        String replace = words.replace(content);
        // 返回结果
        return RuleActionEntity.<RuleMatterEntity>builder()
                .type(LogicCheckTypeVO.SUCCESS)
                .data(RuleMatterEntity.builder().content(replace).build())
                .build();
    }

}