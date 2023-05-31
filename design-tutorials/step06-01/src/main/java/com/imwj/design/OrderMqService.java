package com.imwj.design;

import com.alibaba.fastjson.JSON;
import com.imwj.design.mq.OrderMq;

/**
 * 订单mq消息service
 * @author wj
 * @create 2023-05-31 14:39
 */
public class OrderMqService {

    public void onMessage(String message) {

        OrderMq mq = JSON.parseObject(message, OrderMq.class);

        mq.getUid();
        mq.getOrderId();
        mq.getCreateOrderTime();


        // ... 处理自己的业务
    }
}
