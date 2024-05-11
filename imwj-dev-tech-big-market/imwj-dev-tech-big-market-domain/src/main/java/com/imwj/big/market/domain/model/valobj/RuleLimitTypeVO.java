package com.imwj.big.market.domain.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wj
 * @create 2024-05-09 17:34
 * @description 规则限定枚举值
 */
@Getter
@AllArgsConstructor
public enum RuleLimitTypeVO {

    EQUAL(1, "等于"),
    GT(2, "大于"),
    LT(3, "小于"),
    GE(4, "大于&等于"),
    LE(5, "小于&等于"),
    ENUM(6, "枚举"),
    ;

    private final Integer code;
    private final String info;

}
