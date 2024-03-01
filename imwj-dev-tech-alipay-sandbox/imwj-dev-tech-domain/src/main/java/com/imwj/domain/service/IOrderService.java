package com.imwj.domain.service;

import com.imwj.domain.model.entity.PayOrderEntity;
import com.imwj.domain.model.entity.ShopCartEntity;

/**
 * @author wj
 * @create 2024-02-29 18:14
 * @description
 */
public interface IOrderService {

    /**
     * 通过购物车实体对象，创建支付单实体（用于支付）—— 所有的订单下单都从购物车开始触发
     *
     * @param shopCartEntity 购物车实体
     * @return 支付单实体
     */
    PayOrderEntity createOrder(ShopCartEntity shopCartEntity) throws Exception;

    /**
     * 更新订单状态
     * @param orderId 订单ID
     */
    void changeOrderPaySuccess(String orderId);

}
