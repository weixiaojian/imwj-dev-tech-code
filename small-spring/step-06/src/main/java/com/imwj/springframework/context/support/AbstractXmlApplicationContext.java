package com.imwj.springframework.context.support;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.imwj.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 上下文中对配置信息的加载
 * @author wj
 * @create 2022-11-04 15:39
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException {
        // 1.初始化xml加载器
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        // 2.获取配置文件路径
        String[] configLocations = getConfigLocations();
        if(null != configLocations){
            // 3.加载beanDefinitions
            xmlBeanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 获取本地配置集合
     * @return
     */
    protected abstract String[] getConfigLocations();
}
