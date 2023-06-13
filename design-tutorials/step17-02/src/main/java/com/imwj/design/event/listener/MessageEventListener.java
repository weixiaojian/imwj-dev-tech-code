package com.imwj.design.event.listener;

import com.imwj.design.LotteryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信监听
 * @author wj
 * @create 2023-06-13 14:31
 */
public class MessageEventListener implements EventListener{
    private Logger logger = LoggerFactory.getLogger(MessageEventListener.class);

    @Override
    public void doEvent(LotteryResult result) {
        logger.info("给用户 {} 发送短信通知(短信)：{}", result.getUId(), result.getMsg());
    }
}
