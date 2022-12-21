package com.imwj.springframework.context.event;

import com.imwj.springframework.beans.factory.BeanFactory;
import com.imwj.springframework.context.ApplicationEvent;
import com.imwj.springframework.context.ApplicationListener;

/**
 * @author wj
 * @create 2022-11-23 17:55
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }

}
