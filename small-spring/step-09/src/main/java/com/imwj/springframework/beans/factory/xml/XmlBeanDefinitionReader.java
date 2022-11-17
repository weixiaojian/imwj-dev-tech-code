package com.imwj.springframework.beans.factory.xml;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.imwj.springframework.beans.BeansException;
import com.imwj.springframework.beans.PropertyValue;
import com.imwj.springframework.beans.factory.config.BeanDefinition;
import com.imwj.springframework.beans.factory.config.BeanReference;
import com.imwj.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.imwj.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.imwj.springframework.core.io.Resource;
import com.imwj.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * xml beanDefinition读取
 * @author wj
 * @create 2022-11-01 17:23
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try(InputStream inputStream = resource.getInputStream()){
                doLoadBeanDefinitions(inputStream);
            }
        } catch (Exception e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }


    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for(Resource resource : resources){
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    /**
     * 核心：加载BeanDefinitions
     * @param inputStream
     * @throws ClassNotFoundException
     * @throws BeansException
     */
    private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, BeansException {
        // 0.读取xml文件
        Document doc = XmlUtil.readXML(inputStream);
        // 1.获取根节点
        Element root = doc.getDocumentElement();
        // 2.得到所有字节点
        NodeList childNodes = root.getChildNodes();
        // 3.遍历子节点
        for(int i=0; i< childNodes.getLength(); i++){
            // 判断元素
            if(!(childNodes.item(i) instanceof Element)) continue;
            // 判断对象
            if(!"bean".equals(childNodes.item(i).getNodeName()))continue;

            // 解析标签<bean>
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethod  = bean.getAttribute("init-method");
            String destroyMethodName  = bean.getAttribute("destroy-method");
            // 获取class，方便获取类中的名称
            Class<?> clazz = Class.forName(className);
            // 优先级id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if(StrUtil.isEmpty(beanName)){
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }
            
            // 定义bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            // 读取属性并填充
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if(!(bean.getChildNodes().item(j) instanceof Element))continue;;
                if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) continue;
                // 解析标签<property>
                Element property = (Element) bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");
                // 获取属性值：引入对象、值对象
                Object value  = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if(getRegistry().containsBeanDefinition(beanName)){
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            // 注册 BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }
}
