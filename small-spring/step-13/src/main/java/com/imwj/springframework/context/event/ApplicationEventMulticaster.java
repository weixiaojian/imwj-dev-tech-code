package com.imwj.springframework.context.event;

import com.imwj.springframework.context.ApplicationEvent;
import com.imwj.springframework.context.ApplicationListener;

/**
 * 事件广播器
 * @author wj
 * @create 2022-11-23 16:47
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加一个监听器，以便接收所有事件的通知。
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 从通知列表中删除侦听器。
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件方法。
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
