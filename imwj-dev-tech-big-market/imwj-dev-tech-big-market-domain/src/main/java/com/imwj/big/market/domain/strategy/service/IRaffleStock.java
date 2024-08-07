package com.imwj.big.market.domain.strategy.service;

import com.imwj.big.market.domain.strategy.model.valobj.StrategyAwardStockKeyVo;

/**
 * @author wj
 * @create 2024-05-15 15:21
 * @description 抽奖库存相关服务，获取库存消耗队列
 */
public interface IRaffleStock {

    /**
     * 获取redis中的库存扣减队列
     * @return
     * @throws Exception
     */
    StrategyAwardStockKeyVo takeQueueValue() throws Exception;


    /**
     * 更新数据库中的商品表库存
     * @param strategtId
     * @param awardId
     */
    void updateStrategyAwardStock(Long strategtId, Integer awardId);


}
