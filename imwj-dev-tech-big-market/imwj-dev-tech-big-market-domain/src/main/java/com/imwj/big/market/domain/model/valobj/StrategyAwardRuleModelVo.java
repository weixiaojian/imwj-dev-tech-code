package com.imwj.big.market.domain.model.valobj;

import com.imwj.big.market.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @create 2024-05-07 11:23
 * @description 抽奖策略规则值对象；值对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModelVo {

    private String ruleModels;
}
