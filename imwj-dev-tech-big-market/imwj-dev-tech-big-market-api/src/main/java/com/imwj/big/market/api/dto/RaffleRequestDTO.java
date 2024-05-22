package com.imwj.big.market.api.dto;

import lombok.Data;

/**
 * @author wj
 * @create 2024-05-17 17:13
 * @description 抽奖请求参数
 */
@Data
public class RaffleRequestDTO {

    // 抽奖用户ID
    private String userId;
    // 抽奖策略ID
    private Long strategyId;

}
