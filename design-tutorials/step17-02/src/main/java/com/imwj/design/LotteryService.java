package com.imwj.design;

import com.imwj.design.event.EventManager;
import com.imwj.design.event.listener.MQEventListener;
import com.imwj.design.event.listener.MessageEventListener;

/**
 * @author wj
 * @create 2023-06-13 14:56
 */
public abstract class LotteryService {

    private EventManager eventManager;

    public LotteryService(){
        eventManager = new EventManager(EventManager.EventType.MQ, EventManager.EventType.Message);
        eventManager.subscribe(EventManager.EventType.MQ, new MQEventListener());
        eventManager.subscribe(EventManager.EventType.Message, new MessageEventListener());
    }

    public LotteryResult draw(String uId){
        LotteryResult lotteryResult = doDraw(uId);
        // 通知方法
        eventManager.notify(EventManager.EventType.MQ, lotteryResult);
        eventManager.notify(EventManager.EventType.Message, lotteryResult);
        return lotteryResult;
    }

    /**
     * 真正的业务方法
     * @param uId
     * @return
     */
    protected abstract LotteryResult doDraw(String uId);

}
