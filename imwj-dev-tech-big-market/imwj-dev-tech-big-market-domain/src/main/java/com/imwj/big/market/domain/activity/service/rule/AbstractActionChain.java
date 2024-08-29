package com.imwj.big.market.domain.activity.service.rule;

/**
 * @author wj
 * @create 2024-08-28 17:23
 * @description 抽象活动链
 */
public abstract class AbstractActionChain implements IActionChain{

    private IActionChain next;

    @Override
    public IActionChain next() {
        return next;
    }

    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }
}
