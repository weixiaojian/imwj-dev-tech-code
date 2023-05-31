package com.imwj.design.mq;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单妥投消息
 * @author wj
 * @create 2023-05-31 11:23
 */
@Data
public class POPOrderDelivered {

    private String uId;     // 用户ID
    private String orderId; // 订单号
    private Date orderTime; // 下单时间
    private Date sku;       // 商品
    private Date skuName;   // 商品名称
    private BigDecimal decimal; // 金额
}
