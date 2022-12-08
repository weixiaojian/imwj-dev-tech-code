package com.imwj.springframework.context;

/**
 * @author wj
 * @create 2022-11-23 17:55
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件
     * @param event
     */
    void publishEvent(ApplicationEvent event);

}
