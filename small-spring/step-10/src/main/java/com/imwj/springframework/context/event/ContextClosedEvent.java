package com.imwj.springframework.context.event;

/**
 * 监听关闭事件
 * @author wj
 * @create 2022-11-23 16:45
 */
public class ContextClosedEvent extends ApplicationContextEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
