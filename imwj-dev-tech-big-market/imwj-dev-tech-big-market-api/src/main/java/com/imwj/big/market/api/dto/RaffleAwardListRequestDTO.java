package com.imwj.big.market.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wj
 * @create 2024-05-17 17:02
 * @description 抽奖奖金请求对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardListRequestDTO {

    // 抽奖策略ID
    private Long strategyId;
}
