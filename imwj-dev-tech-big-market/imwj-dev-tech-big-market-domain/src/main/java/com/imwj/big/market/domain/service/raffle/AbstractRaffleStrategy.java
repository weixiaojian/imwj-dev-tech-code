package com.imwj.big.market.domain.service.raffle;

import com.imwj.big.market.domain.model.entity.RaffleAwardEntity;
import com.imwj.big.market.domain.model.entity.RaffleFactorEntity;
import com.imwj.big.market.domain.model.entity.RuleActionEntity;
import com.imwj.big.market.domain.model.entity.StrategyEntity;
import com.imwj.big.market.domain.model.valobj.RuleLogicCheckTypeVO;
import com.imwj.big.market.domain.model.valobj.StrategyAwardRuleModeVo;
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
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionBeforeEntity = this.doCheckRaffleBeforeLogic(
                RaffleFactorEntity.builder()
                        .userId(userId)
                        .strategyId(strategyId)
                        .build(), strategyEntity.ruleModels());

        // 4.针对抽奖前拦截规则做拦截处理
        if(RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionBeforeEntity.getCode())){
            if(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(ruleActionBeforeEntity.getRuleModel())){
                // 黑名单处理（直接返回固定奖品id[积分user1/积分1]）
                return RaffleAwardEntity.builder()
                        .awardId(ruleActionBeforeEntity.getData().getAwardId())
                        .build();
            }else if(DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode().equals(ruleActionBeforeEntity.getRuleModel())){
                // 权重处理（带权重的抽奖流程）
                randomAwardId = strategyDispatch.getRandomAwardId(strategyId, ruleActionBeforeEntity.getData().getRuleWeightValueKey());
                return RaffleAwardEntity.builder()
                        .awardId(randomAwardId)
                        .build();
            }

        }
        // 5.默认的抽奖流程
        randomAwardId = strategyDispatch.getRandomAwardId(strategyId);

        // 6.查询奖品规则[抽奖中（拿到奖品ID时 过滤规则）]、抽奖后（扣减完奖品库存后过滤 抽奖中拦截和无库存走兜底）
        StrategyAwardRuleModeVo strategyAwardRuleModeVo = strategyRepository.queryStrategyAwardRuleMode(strategyId, randomAwardId);

        // 7.抽奖中规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionCenterEntity = this.doCheckRaffleCenterLogic(
                RaffleFactorEntity.builder()
                        .userId(userId)
                        .strategyId(strategyId)
                        .awardId(randomAwardId)
                        .build(), strategyAwardRuleModeVo.raffleCenterRuleModeList());

        // 8.针对抽奖中拦截规则做拦截处理
        if(RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionCenterEntity.getCode())){
            log.info("抽奖中规则拦截，");
            return RaffleAwardEntity.builder()
                    .awardDesc("抽奖中规则拦截成功，用户不满足抽奖次数要求 后续通过抽奖后规则返回幸运奖")
                    .build();
        }

        // 返回抽奖结果
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

    /**
     * 抽奖中规则过滤
     * @param raffleFactorEntity 抽奖因子实体
     * @param logics 需要用到的过滤规则集合
     * @return
     */
    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(
            RaffleFactorEntity raffleFactorEntity, String... logics);
}
