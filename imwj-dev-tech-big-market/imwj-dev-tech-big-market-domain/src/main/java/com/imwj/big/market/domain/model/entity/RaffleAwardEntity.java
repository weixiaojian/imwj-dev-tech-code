package com.imwj.big.market.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wj
 * @create 2024-04-25 17:30
 * @description 抽奖奖品实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardEntity {

    /* 策略ID */
    private Long strategyId;
    /* 奖品ID */
    private Integer awardId;
    /* 奖品对接标识-每一个都是一个对应的发奖策略 */
    private String awardKey;
    /* 奖品配置信息 */
    private String awardConfig;
    /* 奖品内容描述 */
    private String awardDesc;
}
