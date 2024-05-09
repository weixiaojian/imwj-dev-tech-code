package com.imwj.big.market.test.infrastructure.domain;

import com.imwj.big.market.domain.service.armory.IStrategyArmory;
import com.imwj.big.market.domain.service.rule.chatin.ILogicChain;
import com.imwj.big.market.domain.service.rule.chatin.factory.DefaultChainFactory;
import com.imwj.big.market.domain.service.rule.chatin.impl.RuleWeightLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-05-08 18:02
 * @description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicChainTest {
    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;
    @Resource
    private DefaultChainFactory defaultChainFactory;

    @Test
    public void setUp() {
        // 策略装配 100001、100002、100003
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100001L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100002L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100003L));
    }

    /**
     * 黑名单抽奖测试
     */
    @Test
    public void test_LogicChain_rule_blacklist() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("user001", 100001L);
        log.info("测试结果：{}", awardId);
    }

    /**
     * 权重抽奖测试
     */
    @Test
    public void test_LogicChain_rule_weight() {
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 6900L);

        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("imwj", 100001L);
        log.info("测试结果：{}", awardId);
    }

    /**
     * 默认抽奖测试
     */
    @Test
    public void test_LogicChain_rule_default() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("imwj", 100001L);
        log.info("测试结果：{}", awardId);
    }
}
