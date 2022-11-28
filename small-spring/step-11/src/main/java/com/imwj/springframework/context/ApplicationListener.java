package com.imwj.springframework.context;

import java.util.EventListener;

/**
 * @author wj
 * @create 2022-11-23 16:57
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    void onApplicationEvent(E event);
}
