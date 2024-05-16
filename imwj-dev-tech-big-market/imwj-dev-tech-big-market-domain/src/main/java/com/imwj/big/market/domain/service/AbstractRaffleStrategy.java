package com.imwj.big.market.domain.service;

import com.imwj.big.market.domain.model.entity.RaffleAwardEntity;
import com.imwj.big.market.domain.model.entity.RaffleFactorEntity;
import com.imwj.big.market.domain.repository.IStrategyRepository;
import com.imwj.big.market.domain.service.armory.IStrategyDispatch;
import com.imwj.big.market.domain.service.rule.chatin.factory.DefaultChainFactory;
import com.imwj.big.market.domain.service.rule.tree.factory.DefaultTreeFactory;
import com.imwj.big.market.types.enums.ResponseCode;
import com.imwj.big.market.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wj
 * @create 2024-04-25 17:32
 * @description 抽奖抽象类 规范化抽奖流程
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy,IRaffleStock {

    /*仓储服务：数据库、reids操作*/
    protected IStrategyRepository strategyRepository;
    /*调度服务：装配、抽奖操作*/
    protected IStrategyDispatch strategyDispatch;
    protected final DefaultChainFactory defaultChainFactory;
    protected final DefaultTreeFactory defaultTreeFactory;

    public AbstractRaffleStrategy(IStrategyRepository strategyRepository, IStrategyDispatch strategyDispatch
            , DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        this.strategyRepository = strategyRepository;
        this.strategyDispatch = strategyDispatch;
        this.defaultChainFactory = defaultChainFactory;
        this.defaultTreeFactory = defaultTreeFactory;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1.参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if(StringUtils.isBlank(userId) || strategyId == null){
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2.责任链模式过滤规则 抽奖前：黑名单、权重、默认抽奖兜底（黑名单、权重抽奖直接返回，默认抽奖的需要走抽奖中[规则树]）
        DefaultChainFactory.StrategyAwardVO chainStrategyAwardVO = raffleLogicChain(userId, strategyId);
        log.info("抽奖策略计算-责任链 {} {} {} {}", userId, strategyId, chainStrategyAwardVO.getAwardId(), chainStrategyAwardVO.getLogicModel());
        if(!DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode().equals(chainStrategyAwardVO.getLogicModel())){
            return RaffleAwardEntity.builder()
                    .awardId(chainStrategyAwardVO.getAwardId())
                    .build();
        }
        // 3.规则树抽奖过滤 抽奖中/后：抽奖次数、库存扣减、幸运奖兜底
        DefaultTreeFactory.StrategyAwardVO treeStrategyAwardVO = raffleLogicTree(userId, strategyId, chainStrategyAwardVO.getAwardId());
        log.info("抽奖策略计算-规则树 {} {} {} {}", userId, strategyId, treeStrategyAwardVO.getAwardId(), treeStrategyAwardVO.getAwardRuleValue());
        return RaffleAwardEntity.builder()
                .awardId(treeStrategyAwardVO.getAwardId())
                .awardConfig(treeStrategyAwardVO.getAwardRuleValue())
                .build();
    }
    /**
     * 抽奖计算，责任链抽象方法
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    public abstract DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId);

    /**
     * 抽奖结果过滤，决策树抽象方法
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 过滤结果【奖品ID，会根据抽奖次数判断、库存判断、兜底兜里返回最终的可获得奖品信息】
     */
    public abstract DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId);
}
