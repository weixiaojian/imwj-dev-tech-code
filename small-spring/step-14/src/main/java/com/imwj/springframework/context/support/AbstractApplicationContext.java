package com.imwj.springframework.context.support;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.imwj.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.imwj.springframework.beans.factory.config.BeanPostProcessor;
import com.imwj.springframework.beans.factory.support.ApplicationContextAwareProcessor;
import com.imwj.springframework.context.ApplicationEvent;
import com.imwj.springframework.context.ApplicationListener;
import com.imwj.springframework.context.ConfigurableApplicationContext;
import com.imwj.springframework.context.event.ApplicationEventMulticaster;
import com.imwj.springframework.context.event.ContextClosedEvent;
import com.imwj.springframework.context.event.ContextRefreshedEvent;
import com.imwj.springframework.context.event.SimpleApplicationEventMulticaster;
import com.imwj.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 上下文加载器（核心）
 * @author wj
 * @create 2022-11-04 15:39
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    private static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory，并加载spring文件中的BeanDefinition（重点）
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3.添加ApplicationContextAwareProcessor，让继承自ApplicationContextAware的Bean对象都能感知所属的ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (重点，Invoke factory processors registered as beans in the context.)
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. 注册BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作（重点，方便后续执行）
        registerBeanPostProcessors(beanFactory);

        // 6. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        // 7.初始化事件发布者
        initApplicationEventMulticaster();

        // 8.注册事件监听器
        registerListeners();

        // 9.发布容器刷新完成事件
        finishRefresh();
    }



    /**
     * 创建 BeanFactory，并加载spring文件中的BeanDefinition
     * @throws BeansException
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 获取beanFactory
     * @return
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 获取到所有的BeanFactoryPostProcessors并调用
     * @param beanFactory
     * @throws BeansException
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 获取到所有的BeanPostProcessors 并添加到集合中（后续创建bean的时候执行：AbstractAutowireCapableBeanFactory.createBean）
     * @param beanFactory
     * @throws BeansException
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public void registerShutdownHook() {
        // 当jvm关闭时执行当前对象中的close方法
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 执行销毁单例bean的销毁方法
        getBeanFactory().destroySingletons();

        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));
    }

    /**
     * 初始化事件发布者
     */
    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     * 注册事件监听器
     */
    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for(ApplicationListener listener : applicationListeners){
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 发布容器刷新完成事件
     */
    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

}
