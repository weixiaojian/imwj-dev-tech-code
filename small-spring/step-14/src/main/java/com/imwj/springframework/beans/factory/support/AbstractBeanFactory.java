package com.imwj.springframework.beans.factory.support;

import com.imwj.springframework.beans.factory.BeanFactory;
import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.factory.FactoryBean;
import com.imwj.springframework.beans.factory.config.BeanDefinition;
import com.imwj.springframework.beans.factory.config.BeanPostProcessor;
import com.imwj.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.imwj.springframework.utils.ClassUtils;
import com.imwj.springframework.utils.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * beanFactory
 * @author wj
 * @create 2022-10-11 16:43
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /**
     * 储存BeanPostProcessor 方便后续在createBean中执行
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    /**
     * 储存占位符处理器
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    /**
     * 获取类加载器
     */
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    protected <T> T doGetBean(final String name, final Object[] args) throws BeansException {
        Object sharedInstance  = getSingleton(name);
        if (sharedInstance  != null) {
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    /**
     * 添加bean对应的FactoryBean到缓存中去
     * @param beanInstance
     * @param beanName
     * @return
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        // 如果不是FactoryBean 直接返回
        if(!(beanInstance instanceof FactoryBean)){
            return beanInstance;
        }
        // 如果是FactoryBean 则需要添加到缓存中
        Object object = getCachedObjectForFactoryBean(beanName);
        if(object == null){
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }

    /**
     * 获取beanDefinition
     * @param beanName
     * @return
     * @throws BeansException
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 创建bean对象
     * @param beanName
     * @param beanDefinition
     * @param args
     * @return
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }
}
