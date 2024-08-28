package com.imwj.big.market.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wj
 * @create 2024-08-28 15:05
 * @description 订单状态枚举值对象
 */
@Getter
@AllArgsConstructor
public enum OrderStateVO {

    completed("completed", "完成");

    private final String code;
    private final String desc;

}
