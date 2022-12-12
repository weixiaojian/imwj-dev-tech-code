package com.imwj.springframework.beans.factory;

import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.PropertyValue;
import com.imwj.springframework.beans.PropertyValues;
import com.imwj.springframework.beans.factory.config.BeanDefinition;
import com.imwj.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.imwj.springframework.core.io.DefaultResourceLoader;
import com.imwj.springframework.core.io.Resource;
import sun.dc.pr.PRError;

import java.util.Properties;

/**
 * 属性占位符配置
 * @author wj
 * @create 2022-12-08 16:48
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    /**
     * 占位符前缀
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    /**
     * 占位符后缀
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";
    /**
     * 地址
     */
    private String location;


    /**
     * 处理spring.xml中的占位符（${}替换为配置文件中的属性值）
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 加载配置文件到properties中
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            // 得到所有的beanDefinitionName
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for(String beanName : beanDefinitionNames){
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    // 如果属性值不是String类型的化 直接跳过
                    if(!(value instanceof String)){
                        continue;
                    }
                    // 获取属性值
                    String strVal = (String)value;
                    StringBuilder builder = new StringBuilder(strVal);
                    int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx  = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    // 判断属性值是以${}包裹
                    if(startIdx != -1 && stopIdx != -1 && startIdx < stopIdx){
                        // 获取到真正的key
                        String propKey = strVal.substring(startIdx + 2, stopIdx);
                        // 根据key从配置文件中获取对应的属性值
                        String propVal = properties.getProperty(propKey);
                        // 将属性值替换
                        builder.replace(startIdx, stopIdx +1, propVal);
                        // 属性放回到beanDefinition中
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), builder.toString()));
                    }
                }
            }
        } catch (Exception e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
