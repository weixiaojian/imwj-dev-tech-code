package com.imwj.design.event.listener;

import com.imwj.design.LotteryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mq消息监听
 * @author wj
 * @create 2023-06-13 14:32
 */
public class MQEventListener implements EventListener{
    private Logger logger = LoggerFactory.getLogger(MQEventListener.class);

    @Override
    public void doEvent(LotteryResult result) {
        logger.info("记录用户 {} 摇号结果(MQ)：{}", result.getUId(), result.getMsg());
    }
}
