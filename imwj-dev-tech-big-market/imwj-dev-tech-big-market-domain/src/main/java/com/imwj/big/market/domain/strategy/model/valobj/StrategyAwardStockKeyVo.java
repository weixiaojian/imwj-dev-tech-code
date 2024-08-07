package com.imwj.big.market.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wj
 * @create 2024-05-14 17:17
 * @description 库存扣减库存key标识vo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardStockKeyVo {

    /* 策略ID */
    private Long strategyId;
    /* 奖品ID */
    private Integer awardId;
}
