package com.imwj.big.market.domain.service.armory;

import com.imwj.big.market.domain.model.entity.StrategyAwardEntity;
import com.imwj.big.market.domain.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author wj
 * @create 2024-04-24 16:55
 * @description 策略装配库
 */
@Slf4j
@Service
public class StrategyArmory implements IStrategyArmory{


    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        // 1.查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities = strategyRepository.queryStrategyAwardList(strategyId);
        // 2.获取最小概率值（value = 0.0001）
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        // 3.获取概率总和（value = 1）
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4.获得概率范围（概率总和 / 最小概率值 = 10000）
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        // 5.生成策略奖品概率查找表
        List<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for(StrategyAwardEntity strategyAward : strategyAwardEntities){
            // 奖品id
            Integer awardId = strategyAward.getAwardId();
            // 奖品概率（value = 0.0001）
            BigDecimal awardRate = strategyAward.getAwardRate();
            // 计算每个概率值需要存放到查找表的数量并进行填充，即每个奖品占10000中的多少份（10000 * 0.0001 = 1）
            for(int i=0; i<rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i ++){
                strategyAwardSearchRateTables.add(awardId);
            }
        }

        // 6.对存储的奖品进行乱序操作
        Collections.shuffle(strategyAwardSearchRateTables);

        // 7.生成出Map集合
        Map<Integer, Integer> shuffleStrategyAwardSearchRateTable = new HashMap<>();
        for(int i=0; i< strategyAwardSearchRateTables.size(); i++){
            shuffleStrategyAwardSearchRateTable.put(i, strategyAwardSearchRateTables.get(i));
        }

        // 8.存放到Redis
        strategyRepository.storeStrategyAwardSearchRateTable(strategyId, shuffleStrategyAwardSearchRateTable.size(), shuffleStrategyAwardSearchRateTable);

        return true;
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        // 分布式部署下，不一定为当前应用做的策略装配。也就是值不一定会保存到本应用，而是分布式应用，所以需要从 Redis 中获取。
        int rateRange = strategyRepository.getRateRange(strategyId);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return strategyRepository.getStrategyAwardAssemble(strategyId, new SecureRandom().nextInt(rateRange));
    }
}
