package com.imwj.test;

import com.alibaba.fastjson2.JSON;
import com.imwj.domain.factory.DefaultLogicFactory;
import com.imwj.domain.model.entity.RuleActionEntity;
import com.imwj.domain.model.entity.RuleMatterEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-03-22 17:43
 * @description
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Resource
    private DefaultLogicFactory defaultLogicFactory;

    @Test
    public void test(){
        RuleActionEntity<RuleMatterEntity> entity = defaultLogicFactory.doCheckLogic(RuleMatterEntity.builder().content("五星红旗迎风飘扬，毛主席的画像屹立在天安门前。").build(),
                DefaultLogicFactory.LogicModel.SENSITIVE_WORD,
                DefaultLogicFactory.LogicModel.CONTENT_SECURITY);
        log.info("测试结果：{}", JSON.toJSONString(entity));
    }

}
