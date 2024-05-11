package com.imwj.big.market.test.infrastructure.domain;

import com.imwj.big.market.domain.model.entity.RaffleAwardEntity;
import com.imwj.big.market.domain.model.entity.RaffleFactorEntity;
import com.imwj.big.market.domain.service.armory.IStrategyArmory;
import com.imwj.big.market.domain.service.raffle.DefaultRaffleStrategy;
import com.imwj.big.market.domain.service.rule.filter.impl.RuleLockLogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-05-07 14:29
 * @description 带策略校验抽奖测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyTest {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private DefaultRaffleStrategy defaultRaffleStrategy;
    @Resource
    private RuleLockLogicFilter ruleLockLogicFilter;


    /**
     * 抽奖前的装配工作
     */
    @Test
    public void before(){
        strategyArmory.assembleLotteryStrategy(100001L);
        strategyArmory.assembleLotteryStrategy(100002L);
        strategyArmory.assembleLotteryStrategy(100003L);

        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 10L);
    }

    /**
     * 开始抽奖
     */
    @Test
    public void test_defaultRaffleStrategy() {
        RaffleFactorEntity factorEntity = RaffleFactorEntity.builder()
                .strategyId(100003L)
                .userId("user5")
                .build();

        RaffleAwardEntity raffleAwardEntity = defaultRaffleStrategy.performRaffle(factorEntity);
        log.info("奖品id：{}", raffleAwardEntity.getAwardId());
    }


}
