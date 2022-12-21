package com.imwj.springframework.context;

import java.util.EventObject;

/**
 * 应用事件功能的抽象类
 * @author wj
 * @create 2022-11-23 16:40
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
