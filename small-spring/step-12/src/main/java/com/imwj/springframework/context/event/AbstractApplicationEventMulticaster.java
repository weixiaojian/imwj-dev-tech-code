package com.imwj.springframework.context.event;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.BeanFactory;
import com.imwj.springframework.beans.factory.BeanFactoryAware;
import com.imwj.springframework.context.ApplicationEvent;
import com.imwj.springframework.context.ApplicationListener;
import com.imwj.springframework.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.Key;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 事件广播器抽象类
 * @author wj
 * @create 2022-11-23 16:59
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取对应应用监听器
     * @param event
     * @return
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event){
        LinkedList<ApplicationListener> allListeners = new LinkedList<>();
        for(ApplicationListener<ApplicationEvent> listener : applicationListeners){
            if(supportsEvent(listener, event)){
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    /**
     * 判断监听器是否对该事件感兴趣
     * @param applicationListener 监听器
     * @param event 事件
     * @return
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // 按照CglibSubclassingInstantiationStrategy、SimpleInstantiationStrategy不同实例化类型，需要判断后获取目标class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        Type genericInterface = targetClass.getGenericInterfaces()[0];

        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        }catch (ClassNotFoundException e){
            throw new BeansException("wrong event class name: " + className);
        }

        // 判定此eventClassName对象所标识的类或接口与指定的event.getClass()参数所表示的类或接口是否相同，或是否是其他超类或超接口
        // isAssignableFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有类的终极父类都是Object。如果A.isAssignableFrom(B)结果是true，证明B可以转换成为A,也就是A可以由B转换而来。
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
