package com.imwj.domain.repository;

import com.imwj.domain.model.aggregate.CreateOrderAggregate;
import com.imwj.domain.model.entity.OrderEntity;
import com.imwj.domain.model.entity.PayOrderEntity;
import com.imwj.domain.model.entity.ProductEntity;
import com.imwj.domain.model.entity.ShopCartEntity;

/**
 * @author wj
 * @create 2024-02-29 18:13
 * @description 数据库操作
 */
public interface IOrderRepository {

    /**
     * 查询未支付订单
     *
     * @param shopCartEntity 购物车实体对象
     * @return 订单实体对象
     */
    OrderEntity queryUnPayOrder(ShopCartEntity shopCartEntity);

    /**
     * 模拟查询商品信息
     *
     * @param productId 商品ID
     * @return 商品实体对象
     */
    ProductEntity queryProductByProductId(String productId);

    /**
     * 保存订单对象
     *
     * @param orderAggregate 订单聚合
     */
    void doSaveOrder(CreateOrderAggregate orderAggregate);

    /**
     * 更新订单支付信息
     *
     * @param payOrderEntity 支付单
     */
    void updateOrderPayInfo(PayOrderEntity payOrderEntity);

    /**
     * 订单支付成功
     * @param orderId 订单ID
     */
    void changeOrderPaySuccess(String orderId);

}
