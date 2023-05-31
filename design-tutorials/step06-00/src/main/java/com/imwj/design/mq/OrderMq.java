package com.imwj.design.mq;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * @author wj
 * @create 2023-05-31 11:23
 */
@Data
public class OrderMq {


    private String uid;           // 用户ID
    private String sku;           // 商品
    private String orderId;       // 订单ID
    private Date createOrderTime; // 下单时间


}
