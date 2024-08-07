package com.imwj.big.market.domain.strategy.service.rule.chatin;

/**
 * @author wj
 * @create 2024-05-08 17:08
 * @description
 */
public abstract class AbstractLongChain implements ILogicChain{

    private ILogicChain next;

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        return this.next = next;
    }

    @Override
    public ILogicChain next() {
        return next;
    }

    protected abstract String ruleModel();
}
