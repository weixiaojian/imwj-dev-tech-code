package com.imwj.big.market.domain.activity.service.rule;

/**
 * @author wj
 * @create 2024-08-28 17:24
 * @description 下单规则过滤接口工厂
 */
public interface IActionChainArmory {

    IActionChain next();

    IActionChain appendNext(IActionChain next);

}
