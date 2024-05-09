package com.imwj.big.market.domain.service.rule.chatin;

/**
 * @author wj
 * @create 2024-05-08 17:57
 * @description 装配接口
 */
public interface ILogicChainArmory {

    /**
     * 添加责任链的下一个节点
     * @param next
     * @return
     */
    ILogicChain appendNext(ILogicChain next);

    /**
     * 获取责任链的下一个节点
     * @return
     */
    ILogicChain next();
}
