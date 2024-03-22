package com.imwj.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wj
 * @create 2024-03-22 16:34
 * @description 规则过滤结果对象
 */
@Getter
@AllArgsConstructor
public enum LogicCheckTypeVO {

    SUCCESS("0000", "校验通过"),
    REFUSE("0001","校验拒绝"),
    ;

    private final String code;
    private final String info;

}

