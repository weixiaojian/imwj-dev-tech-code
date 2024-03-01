package com.imwj.trigger.listener;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author wj
 * @create 2024-02-29 18:02
 * @description
 */
@Slf4j
@Component
public class OrderPaySuccessListener {

    @Subscribe
    public void handleEvent(String orderId) {
        log.info("收到支付成功消息，可以做接下来的事情了【发货、充值、开会员】orderId：{}", orderId);
    }

}
