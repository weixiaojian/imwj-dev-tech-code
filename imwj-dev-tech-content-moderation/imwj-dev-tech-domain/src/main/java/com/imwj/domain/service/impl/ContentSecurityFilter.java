package com.imwj.domain.service.impl;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.imwj.domain.annotation.LogicStrategy;
import com.imwj.domain.factory.DefaultLogicFactory;
import com.imwj.domain.model.entity.RuleActionEntity;
import com.imwj.domain.model.entity.RuleMatterEntity;
import com.imwj.domain.model.vo.LogicCheckTypeVO;
import com.imwj.domain.service.IRuleLogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-03-22 17:33
 * @description 内容审核
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.CONTENT_SECURITY)
public class ContentSecurityFilter implements IRuleLogicFilter {

    @Resource
    private AipContentCensor aipContentCensor;

    @Override
    public RuleActionEntity<RuleMatterEntity> filter(RuleMatterEntity ruleMatterEntity) {
        JSONObject jsonObject = aipContentCensor.textCensorUserDefined(ruleMatterEntity.getContent());
        if (!jsonObject.isNull("conclusion") && "不合规".equals(jsonObject.get("conclusion"))) {
            return RuleActionEntity.<RuleMatterEntity>builder()
                    .type(LogicCheckTypeVO.REFUSE)
                    .data(RuleMatterEntity.builder().content("内容不合规").build())
                    .build();
        }
        // 返回结果
        return RuleActionEntity.<RuleMatterEntity>builder()
                .type(LogicCheckTypeVO.SUCCESS)
                .data(ruleMatterEntity)
                .build();
    }

}
