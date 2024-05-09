package com.imwj.big.market.domain.service.annotaion;

import com.imwj.big.market.domain.service.rule.filter.factory.DefaultLogicFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wj
 * @create 2024-04-25 17:46
 * @description 自定义策略枚举(用户标识那些类是策略类)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogicStrategy {

    DefaultLogicFactory.LogicModel logicMode();
}
