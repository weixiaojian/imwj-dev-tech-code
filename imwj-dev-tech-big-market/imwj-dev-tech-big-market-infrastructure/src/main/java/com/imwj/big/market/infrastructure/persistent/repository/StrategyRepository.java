package com.imwj.big.market.infrastructure.persistent.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imwj.big.market.domain.model.entity.StrategyAwardEntity;
import com.imwj.big.market.domain.model.entity.StrategyEntity;
import com.imwj.big.market.domain.model.entity.StrategyRuleEntity;
import com.imwj.big.market.domain.model.valobj.*;
import com.imwj.big.market.domain.repository.IStrategyRepository;
import com.imwj.big.market.infrastructure.persistent.dao.*;
import com.imwj.big.market.infrastructure.persistent.po.*;
import com.imwj.big.market.infrastructure.persistent.redis.IRedisService;
import com.imwj.big.market.types.common.Constants;
import com.imwj.big.market.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.imwj.big.market.types.enums.ResponseCode.UN_ASSEMBLED_STRATEGY_ARMORY;

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
    @Resource
    private IRuleTreeDao ruleTreeDao;
    @Resource
    private IRuleTreeNodeDao ruleTreeNodeDao;
    @Resource
    private IRuleTreeNodeLineDao ruleTreeNodeLineDao;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        List<StrategyAwardEntity> strategyAwardEntities = new ArrayList<>();
        // 1.优先从缓存中获取
        /*String cachekey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        JSONArray jsonStr = redisService.getValue(cachekey);
        if(jsonStr != null){
            strategyAwardEntities = jsonStr.toJavaList(StrategyAwardEntity.class);
            return strategyAwardEntities;
        }*/
        // 2.缓存中没有再从数据库获取
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardList(strategyId);
        // 3.存入缓存并返回
        strategyAwardEntities = new ArrayList<>();
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardTitle(strategyAward.getAwardTitle())
                    .awardSubTitle(strategyAward.getAwardSubtitle())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .sort(strategyAward.getSort())
                    .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }
        // redisService.setValue(cachekey, strategyAwardEntities);
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
        String key = Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId;
        if(!redisService.isExists(key)){
            throw new AppException(UN_ASSEMBLED_STRATEGY_ARMORY.getCode(), key + Constants.COLON + UN_ASSEMBLED_STRATEGY_ARMORY.getInfo());
        }
        return redisService.getValue(key);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        StrategyEntity strategyEntity = null;
        // 0.优先从缓存获取
        /*String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        JSONObject jsonObject = redisService.getValue(cacheKey);
        if(jsonObject  != null){
            strategyEntity = jsonObject.toJavaObject(StrategyEntity.class);
            return strategyEntity;
        }*/
        // 1.查询数据库中的数据
        Strategy strategyDb = strategyDao.queryStrategyByStrategyId(strategyId);
        // 2.转换为充血实体
        strategyEntity = StrategyEntity.builder()
                .strategyId(strategyDb.getStrategyId())
                .strategyDesc(strategyDb.getStrategyDesc())
                .ruleModels(strategyDb.getRuleModels())
                .build();
        // redisService.setValue(cacheKey, strategyEntity);
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
    public String queryStrategyRuleValue(Long strategyId, String ruleModel) {
        return queryStrategyRuleValue(strategyId, null, ruleModel);
    }

    @Override
    public StrategyAwardRuleModelVo queryStrategyAwardRuleModel(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        String ruleModels = strategyAwardDao.queryStrategyAwardRuleModels(strategyAward);
        return StrategyAwardRuleModelVo.builder().ruleModels(ruleModels).build();
    }

    @Override
    public RuleTreeVO queryRuleTreeVoByTreeId(String treeId) {
        // 0.优先从缓存获取
       /* String cacheKey = Constants.RedisKey.RULE_TREE_VO_KEY + treeId;
        String jsonStr = redisService.getValue(cacheKey);
        if(jsonStr != null){
            return JSON.parseObject(jsonStr, RuleTreeVO.class);
        }*/

        // 1.查询数据库中的数据
        RuleTree ruleTree = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        List<RuleTreeNode> ruleTreeNodes = ruleTreeNodeDao.queryRuleTreeNodeListByTreeId(treeId);
        List<RuleTreeNodeLine> ruleTreeNodeLines = ruleTreeNodeLineDao.queryRuleTreeNodeLineListByTreeId(treeId);

        // 2.tree node line转换Map结构
        Map<String, List<RuleTreeNodeLineVO>> ruleTreeNodeLineMap = new HashMap<>();
        for(RuleTreeNodeLine ruleTreeNodeLine : ruleTreeNodeLines){
            RuleTreeNodeLineVO ruleTreeNodeLineVO = RuleTreeNodeLineVO.builder()
                    .treeId(ruleTreeNodeLine.getTreeId())
                    .ruleNodeFrom(ruleTreeNodeLine.getRuleNodeFrom())
                    .ruleNodeTo(ruleTreeNodeLine.getRuleNodeTo())
                    .ruleLimitType(RuleLimitTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitType()))
                    .ruleLimitValue(RuleLogicCheckTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitValue()))
                    .build();

            List<RuleTreeNodeLineVO> ruleTreeNodeLineVOList = ruleTreeNodeLineMap.computeIfAbsent(ruleTreeNodeLine.getRuleNodeFrom(), k -> new ArrayList<>());
            ruleTreeNodeLineVOList.add(ruleTreeNodeLineVO);
        }

        // 3.tree node 转换为map结构
        Map<String, RuleTreeNodeVO> ruleTreeNodeVOMap = new HashMap<>();
        for(RuleTreeNode ruleTreeNode : ruleTreeNodes){
            RuleTreeNodeVO ruleTreeNodeVO = RuleTreeNodeVO.builder()
                    .treeId(ruleTreeNode.getTreeId())
                    .ruleKey(ruleTreeNode.getRuleKey())
                    .ruleDesc(ruleTreeNode.getRuleDesc())
                    .ruleValue(ruleTreeNode.getRuleValue())
                    .treeNodeLineVOList(ruleTreeNodeLineMap.get(ruleTreeNode.getRuleKey()))
                    .build();
            ruleTreeNodeVOMap.put(ruleTreeNode.getRuleKey(), ruleTreeNodeVO);
        }

        // 4.构建ruleTree
        RuleTreeVO ruleTreeVODB = RuleTreeVO.builder()
                .treeId(ruleTree.getTreeId())
                .treeName(ruleTree.getTreeName())
                .treeDesc(ruleTree.getTreeDesc())
                .treeRootRuleNode(ruleTree.getTreeRootRuleKey())
                .treeNodeMap(ruleTreeNodeVOMap)
                .build();

        // redisService.setValue(cacheKey, JSON.toJSONString(ruleTreeVODB));
        return ruleTreeVODB;
    }

    @Override
    public void cacheStrategyAwardCount(String cacheKey, Integer awardCount) {
        // 判断这个key是否装配过
        if(redisService.isExists(cacheKey)){
            return;
        }
        // 未装配过重新装配
        redisService.setAtomicLong(cacheKey, awardCount);
    }

    @Override
    public Boolean subtractionAwardStock(String key) {
        // 扣减库存（得到剩余库存）
        long surplus = redisService.decr(key);
        if(surplus < 0){
            redisService.setValue(key, 0);
            return false;
        }
        // 加锁操作
        String lockKey = key + Constants.UNDERLINE + surplus;
        Boolean lock = redisService.setNx(lockKey);
        // 加锁失败（说明已经扣减了该商品的库存）、加锁成功（说明正常扣减库存）
        if(!lock){
            log.info("策略奖品库存加锁失败 {}", lockKey);
        }
        return lock;
    }

    @Override
    public void awardStockConsumeSendQueue(StrategyAwardStockKeyVo strategyAwardStockKeyVo) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUERY_KEY;
        RBlockingQueue<StrategyAwardStockKeyVo> blockingQueue = redisService.getBlockingQueue(cacheKey);
        RDelayedQueue<StrategyAwardStockKeyVo> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        // 3秒后再加入延迟队列
        delayedQueue.offer(strategyAwardStockKeyVo, 3, TimeUnit.SECONDS);
    }

    @Override
    public StrategyAwardStockKeyVo takeQueueValue() {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUERY_KEY;
        RBlockingQueue<JSONObject> blockingQueue = redisService.getBlockingQueue(cacheKey);
        JSONObject jsonObject = blockingQueue.poll();
        if(jsonObject == null){
            return null;
        }
        StrategyAwardStockKeyVo stockKeyVo = jsonObject.toJavaObject(StrategyAwardStockKeyVo.class);
        return stockKeyVo;
    }

    @Override
    public void updateStrategyAwardStock(Long strategtId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategtId);
        strategyAward.setAwardId(awardId);
        strategyAwardDao.updateStrategyAwardStock(strategyAward);
    }

    @Override
    public StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId) {
        // 查询数据库
        StrategyAward strategyAward = strategyAwardDao.queryStrategyAward(strategyId, awardId);
        // 转换实体
        StrategyAwardEntity strategyAwardEntity = new StrategyAwardEntity();
        BeanUtils.copyProperties(strategyAward, strategyAwardEntity);
        return strategyAwardEntity;
    }
}
