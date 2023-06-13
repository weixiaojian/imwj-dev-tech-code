package com.imwj.design.event.listener;

import com.imwj.design.LotteryResult;

/**
 * 监听接口
 * @author wj
 * @create 2023-06-13 14:27
 */
public interface EventListener {

    void doEvent(LotteryResult result);
}
