package com.imwj.big.market.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.imwj.big.market.domain.strategy.model.valobj.RuleTreeVO;
import com.imwj.big.market.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-05-11 15:02
 * @description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRepositoryTest {

    @Resource
    private IStrategyRepository strategyRepository;

    @Test
    public void test_queryRuleTreeVoByTreeId(){
        RuleTreeVO ruleTreeVO = strategyRepository.queryRuleTreeVoByTreeId("tree_lock");
        log.info(JSON.toJSONString(ruleTreeVO));
    }

}
