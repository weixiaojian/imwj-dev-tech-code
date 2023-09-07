package com.imwj.mybatis.builder.xml;

import com.imwj.mybatis.builder.BaseBuilder;
import com.imwj.mybatis.builder.MapperBuilderAssistant;
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
    // 映射器构建助手
    private MapperBuilderAssistant builderAssistant;

    public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource) throws DocumentException {
        this(new SAXReader().read(inputStream), configuration, resource);
    }

    private XMLMapperBuilder(Document document, Configuration configuration, String resource){
        super(configuration);
        this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
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
            configuration.addMapper(Resources.classForName(builderAssistant.getCurrentNamespace()));
        }
    }

    /**
     * 配置mapper元素
     * @param element
     */
    private void configurationElement(Element element) {
        // 1.配置namespace
        String namespace = element.attributeValue("namespace");
        if (namespace.equals("")) {
            throw new RuntimeException("Mapper's namespace cannot be empty");
        }
        builderAssistant.setCurrentNamespace(namespace);
        // 2.配置select|insert|update|delete
        buildStatementFromContext(element.elements("select"));
    }

    /**
     * 配置select|insert|update|delete
     * @param list
     */
    private void buildStatementFromContext(List<Element> list) {
        for(Element element : list){
            final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, builderAssistant, element);
            statementParser.parseStatementNode();
        }
    }
}
