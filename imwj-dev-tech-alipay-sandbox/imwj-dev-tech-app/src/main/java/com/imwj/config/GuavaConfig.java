package com.imwj.config;

import com.google.common.eventbus.EventBus;
import com.imwj.trigger.listener.OrderPaySuccessListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wj
 * @create 2024-02-29 17:34
 * @description
 */
@Configuration
public class GuavaConfig {

    @Bean
    public EventBus eventBusListener(OrderPaySuccessListener listener) {
        EventBus eventBus = new EventBus();
        eventBus.register(listener);
        return eventBus;
    }

}
