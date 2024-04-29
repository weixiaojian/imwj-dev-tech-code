package com.imwj.big.market.domain.service.raffle;

import com.imwj.big.market.domain.model.entity.RaffleAwardEntity;
import com.imwj.big.market.domain.model.entity.RaffleFactorEntity;
import com.imwj.big.market.domain.model.entity.RuleActionEntity;
import com.imwj.big.market.domain.model.entity.StrategyEntity;
import com.imwj.big.market.domain.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.repository.IStrategyRepository;
import com.imwj.big.market.domain.service.IRaffleStrategy;
import com.imwj.big.market.domain.service.armory.IStrategyArmory;
import com.imwj.big.market.domain.service.armory.IStrategyDispatch;
import com.imwj.big.market.domain.service.rule.factory.DefaultLogicFactory;
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
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    /*仓储服务：数据库、reids操作*/
    protected IStrategyRepository strategyRepository;
    /*调度服务：装配、抽奖操作*/
    protected IStrategyDispatch strategyDispatch;

    public AbstractRaffleStrategy(IStrategyRepository strategyRepository, IStrategyDispatch strategyDispatch) {
        this.strategyRepository = strategyRepository;
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        Integer randomAwardId = null;
        // 1.参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if(StringUtils.isBlank(userId) || strategyId == null){
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2.策略查询
        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);

        // 3.抽奖前规则过滤
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = this.doCheckRaffleBeforeLogic(
                RaffleFactorEntity.builder().userId(userId).strategyId(strategyId).build(), strategyEntity.ruleModels());

        // 4.针对拦截规则做拦截处理
        if(RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionEntity.getCode())){
            if(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(ruleActionEntity.getRuleModel())){
                // 黑名单处理（直接返回固定奖品id[积分user1/积分1]）
                return RaffleAwardEntity.builder()
                        .awardId(ruleActionEntity.getData().getAwardId())
                        .build();
            }else if(DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode().equals(ruleActionEntity.getRuleModel())){
                // 权重处理（带权重的抽奖流程）
                randomAwardId = strategyDispatch.getRandomAwardId(strategyId, ruleActionEntity.getData().getRuleWeightValueKey());
                return RaffleAwardEntity.builder()
                        .awardId(randomAwardId)
                        .build();
            }

        }
        // 5.默认的抽奖流程
        randomAwardId = strategyDispatch.getRandomAwardId(strategyId);

        return RaffleAwardEntity.builder()
                .awardId(randomAwardId)
                .build();
    }

    /**
     * 抽奖前规则过滤
     * @param raffleFactorEntity 抽奖因子实体
     * @param logics 需要用到的过滤规则集合
     * @return
     */
    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(
            RaffleFactorEntity raffleFactorEntity, String... logics);
}
