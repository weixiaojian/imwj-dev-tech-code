package com.imwj.big.market.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wj
 * @create 2024-08-28 15:04
 * @description 活动状态值对象
 */
@Getter
@AllArgsConstructor
public enum ActivityStateVO {

    create("create", "创建");

    private final String code;
    private final String desc;

}

