package com.imwj.mybatis.builder.xml;

import com.imwj.mybatis.builder.BaseBuilder;
import com.imwj.mybatis.datasource.DataSourceFactory;
import com.imwj.mybatis.io.Resources;
import com.imwj.mybatis.mapping.BoundSql;
import com.imwj.mybatis.mapping.Environment;
import com.imwj.mybatis.mapping.MappedStatement;
import com.imwj.mybatis.mapping.SqlCommandType;
import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.transaction.TransactionFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wj
 * @create 2023-07-20 17:39
 * @description xml解析器
 */
public class XmlConfigBuilder extends BaseBuilder {

    private Element root;

    public XmlConfigBuilder(Reader reader) {
        // 1.调用父类初始化configuration
        super(new Configuration());
        // 2.dom4j处理xml
        SAXReader saxReader = new SAXReader();
        try {
            Document read = saxReader.read(new InputSource(reader));
            root = read.getRootElement();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析xml中的mappers
     * @return
     */
    public Configuration parse(){
        try {
            // 环境
            environmentsElement(root.element("environments"));
            // 解析映射器
            mapperElement(root.element("mappers"));
        }catch (Exception e){
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
        return configuration;
    }

    /**
     * 解析环境、数据库连接信息
     * @param context
     */
    private void environmentsElement(Element context) throws Exception {
        String environment  = context.attributeValue("default");
        List<Element> environmentList = context.elements("environment");
        for(Element e : environmentList){
            String id = e.attributeValue("id");
            if(environment.equals(id)){
                // 事务管理器
                TransactionFactory txFactory = (TransactionFactory) typeAliasRegistry.resolveAlias(e.element("transactionManager").attributeValue("type")).newInstance();
                // 数据源
                Element dataSourceElement = e.element("dataSource");
                DataSourceFactory dataSourceFactory = (DataSourceFactory) typeAliasRegistry.resolveAlias(dataSourceElement.attributeValue("type")).newInstance();
                List<Element> propertyList = dataSourceElement.elements("property");
                Properties props = new Properties();
                for(Element property : propertyList){
                    props.setProperty(property.attributeValue("name"), property.attributeValue("value"));
                }
                dataSourceFactory.setProperties(props);
                // 得到数据源dataSource
                DataSource dataSource = dataSourceFactory.getDataSource();
                // 构建环境 并挂载到configuration
                Environment.Builder environmentBuilder  = new Environment.Builder(id)
                        .transactionFactory(txFactory)
                        .dataSource(dataSource);
                configuration.setEnvironment(environmentBuilder.build());
            }
        }
    }

    /**
     * 解析指定标签mapper
     /*
     * <mappers>
     *	 <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
     *	 <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
     *	 <mapper resource="org/mybatis/builder/PostMapper.xml"/>
     * </mappers>
     */
    private void mapperElement(Element mappers) throws Exception {
        List<Element> elementList = mappers.elements("mapper");
        for(Element element : elementList){
            String resource = element.attributeValue("resource");
            InputStream inputStream = Resources.getResourceAsStream(resource);

            // 在for循环中每一个mapper都哦new一个XMLMapperBuilder解析
            XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(inputStream, configuration, resource);
            mapperBuilder.pares();
        }
    }
}
