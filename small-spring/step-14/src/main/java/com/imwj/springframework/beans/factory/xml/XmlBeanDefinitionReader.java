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
import com.imwj.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import com.imwj.springframework.core.io.Resource;
import com.imwj.springframework.core.io.ResourceLoader;
import cn.hutool.core.util.StrUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
    private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, BeansException, DocumentException {
        // 0.读取xml文件
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 1.获取根节点
        Element root = document.getRootElement();
        // 1.1 解析context:component-scan 标签  用于组装 BeanDefinition
        Element conmponentScan = root.element("component-scan");
        if(conmponentScan != null){
            String scanPath = conmponentScan.attributeValue("base-package");
            if(StrUtil.isBlank(scanPath)){
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            scanPackage(scanPath);
        }
        // 2.得到所有字节点
        List<Element> beanList = root.elements("bean");
        // 3.遍历子节点
        for (Element bean : beanList) {
            // 解析标签<bean>
            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");
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
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }
            // 读取属性并填充
            List<Element> propertyList = bean.elements("property");
            for (Element property : propertyList) {
                // 解析标签：property
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");
                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
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

    /**
     * 扫描指定路径的bean 并加载为beanDefinition
     * @param scanPath
     */
    private void scanPackage(String scanPath) {
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }
}
