package com.imwj.big.market.infrastructure.persistent.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imwj.big.market.domain.model.entity.StrategyAwardEntity;
import com.imwj.big.market.domain.model.entity.StrategyEntity;
import com.imwj.big.market.domain.model.entity.StrategyRuleEntity;
import com.imwj.big.market.domain.model.valobj.StrategyAwardRuleModeVo;
import com.imwj.big.market.domain.repository.IStrategyRepository;
import com.imwj.big.market.domain.service.armory.IStrategyArmory;
import com.imwj.big.market.infrastructure.persistent.dao.IStrategyAwardDao;
import com.imwj.big.market.infrastructure.persistent.dao.IStrategyDao;
import com.imwj.big.market.infrastructure.persistent.dao.IStrategyRuleDao;
import com.imwj.big.market.infrastructure.persistent.po.Strategy;
import com.imwj.big.market.infrastructure.persistent.po.StrategyAward;
import com.imwj.big.market.infrastructure.persistent.po.StrategyRule;
import com.imwj.big.market.infrastructure.persistent.redis.IRedisService;
import com.imwj.big.market.infrastructure.persistent.redis.RedissonService;
import com.imwj.big.market.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @create 2024-04-24 17:23
 * @description 策略仓储实现
 */
@Slf4j
@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyRuleDao strategyRuleDao;
    @Resource
    private IStrategyAwardDao strategyAwardDao;
    @Resource
    private IRedisService redisService;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        // 1.优先从缓存中获取
        List<StrategyAwardEntity> strategyAwardEntities = new ArrayList<>();
        String cachekey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        JSONArray jsonStr = redisService.getValue(cachekey);
        if(jsonStr != null){
            strategyAwardEntities = jsonStr.toJavaList(StrategyAwardEntity.class);
            return strategyAwardEntities;
        }
        // 2.缓存中没有再从数据库获取
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardList(strategyId);
        // 3.存入缓存并返回
        strategyAwardEntities = new ArrayList<>();
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }
        redisService.setValue(cachekey, strategyAwardEntities);
        return strategyAwardEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable) {
        // 1. 存储抽奖策略范围值，如10000，用于生成1000以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, rateRange);
        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);
        cacheRateTable.putAll(strategyAwardSearchRateTable);
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, Integer rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);
    }


    @Override
    public int getRateRange(String strategyId) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        // 0.优先从缓存获取
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        JSONObject jsonObject = redisService.getValue(cacheKey);
        StrategyEntity strategyEntity = null;
        if(jsonObject  != null){
            strategyEntity = jsonObject.toJavaObject(StrategyEntity.class);
            return strategyEntity;
        }
        // 1.查询数据库中的数据
        Strategy strategyDb = strategyDao.queryStrategyByStrategyId(strategyId);
        // 2.转换为充血实体
        strategyEntity = StrategyEntity.builder()
                .strategyId(strategyDb.getStrategyId())
                .strategyDesc(strategyDb.getStrategyDesc())
                .ruleModels(strategyDb.getRuleModels())
                .build();
        redisService.setValue(cacheKey, strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        // 1.查询数据库中的数据
        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setRuleModel(ruleModel);
        StrategyRule strategyRuleDb = strategyRuleDao.queryStrategyRule(strategyRule);
        // 2.转换为充血实体
        return StrategyRuleEntity.builder()
                .strategyId(strategyRuleDb.getStrategyId())
                .awardId(strategyRuleDb.getAwardId())
                .ruleType(strategyRuleDb.getRuleType())
                .ruleModel(strategyRuleDb.getRuleModel())
                .ruleValue(strategyRuleDb.getRuleValue())
                .ruleDesc(strategyRuleDb.getRuleDesc())
                .build();
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRule strategyRuleReq = new StrategyRule();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setAwardId(awardId);
        strategyRuleReq.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRuleReq);
    }

    @Override
    public StrategyAwardRuleModeVo queryStrategyAwardRuleMode(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        String ruleModes = strategyAwardDao.queryStrategyAwardRuleModes(strategyAward);
        return StrategyAwardRuleModeVo.builder().ruleModes(ruleModes).build();
    }
}
