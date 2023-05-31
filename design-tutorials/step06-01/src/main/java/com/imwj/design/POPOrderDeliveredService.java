package com.imwj.design;

import com.alibaba.fastjson.JSON;
import com.imwj.design.mq.POPOrderDelivered;

/**
 * pop订单消息service
 * @author wj
 * @create 2023-05-31 14:39
 */
public class POPOrderDeliveredService {

    public void onMessage(String message) {

        POPOrderDelivered mq = JSON.parseObject(message, POPOrderDelivered.class);

        mq.getUId();
        mq.getOrderId();
        mq.getOrderTime();

        // ... 处理自己的业务
    }
}
