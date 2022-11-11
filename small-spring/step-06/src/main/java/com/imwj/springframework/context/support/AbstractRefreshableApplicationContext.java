package com.imwj.springframework.context.support;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.imwj.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 获取Bean工厂和加载资源
 * @author wj
 * @create 2022-11-04 15:39
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        // 1.创建beanFactory对象
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        // 2.加载spring.xml配置文件中的beanDefinitions
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 加载spring.xml配置文件中的beanDefinitions
     * @param beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException;

    /**
     * 创建benaFactory
     * @return
     */
    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
