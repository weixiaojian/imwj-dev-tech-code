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


    /* 奖品ID */
    private Integer awardId;
    /* 奖品配置信息 */
    private String awardConfig;
    /*奖品排序*/
    private Integer sort;

}
