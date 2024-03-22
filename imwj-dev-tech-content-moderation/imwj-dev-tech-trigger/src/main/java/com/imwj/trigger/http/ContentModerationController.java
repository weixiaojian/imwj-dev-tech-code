package com.imwj.trigger.http;

import com.alibaba.fastjson.JSON;
import com.imwj.domain.factory.DefaultLogicFactory;
import com.imwj.domain.model.entity.RuleActionEntity;
import com.imwj.domain.model.entity.RuleMatterEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-03-22 17:41
 * @description
 */
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/content/")
public class ContentModerationController {

    @Resource
    private DefaultLogicFactory defaultLogicFactory;

    /**
     * 内容审核
     * 接口【敏感词过滤】；http://localhost:8091/api/v1/content/sensitive/rule?content=五星红旗迎风飘扬，毛主席的画像屹立在天安门前。
     * 接口【内容安全拦截】；http://localhost:8091/api/v1/content/sensitive/rule?content=五星红旗迎风飘扬，毛主席的画像屹立在天安门前。
     * @param content 文本
     * @return 审核后结果
     */
    @RequestMapping(value = "sensitive/rule", method = RequestMethod.GET)
    public String rule(String content) {
        try {
            log.info("内容审核开始 content: {}", content);
            RuleActionEntity<RuleMatterEntity> entity = defaultLogicFactory.doCheckLogic(RuleMatterEntity.builder().content(content).build(),
                    DefaultLogicFactory.LogicModel.SENSITIVE_WORD,
                    DefaultLogicFactory.LogicModel.CONTENT_SECURITY
            );
            log.info("内容审核完成 content: {}", entity.getData());
            return JSON.toJSONString(entity);
        } catch (Exception e) {
            log.error("内容审核异常 content: {}", content, e);
            return "Err!";
        }
    }

}
