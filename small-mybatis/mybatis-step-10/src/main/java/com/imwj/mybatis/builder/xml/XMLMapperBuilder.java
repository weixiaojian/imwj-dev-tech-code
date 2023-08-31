package com.imwj.mybatis.builder.xml;

import com.imwj.mybatis.builder.BaseBuilder;
import com.imwj.mybatis.io.Resources;
import com.imwj.mybatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author wj
 * @create 2023-08-18 11:17
 * @description XML映射构建器
 */
public class XMLMapperBuilder extends BaseBuilder {

    private Element element;
    private String  resource;
    private String currentNamespace;

    public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource) throws DocumentException {
        this(new SAXReader().read(inputStream), configuration, resource);
    }

    private XMLMapperBuilder(Document document, Configuration configuration, String resource){
        super(configuration);
        this.element = document.getRootElement();
        this.resource = resource;
    }

    /**
     * 解析
     */
    public void pares()throws Exception{
        // 如果当前资源没有加载过(防止重复加载)
        if(!configuration.isResourceLoaded(resource)){
            // 加载element
            configurationElement(element);
            // 标记已加载过该资源
            configuration.addLoadedResource(resource);
            // 绑定映射器到namespace
            configuration.addMapper(Resources.classForName(currentNamespace));
        }
    }

    /**
     * 配置mapper元素
     * @param element
     */
    private void configurationElement(Element element) {
        // 1.配置namespace
        currentNamespace = element.attributeValue("namespace");
        if("".equals(currentNamespace)){
            throw new RuntimeException("Mapper's namespace cannot be empty");
        }
        // 2.配置select|insert|update|delete
        buildStatementFromContext(element.elements("select"));
    }

    /**
     * 配置select|insert|update|delete
     * @param list
     */
    private void buildStatementFromContext(List<Element> list) {
        for(Element element : list){
            final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, element, currentNamespace);
            statementParser.parseStatementNode();
        }
    }
}
