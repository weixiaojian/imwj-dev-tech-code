package com.imwj.mybatis.builder.xml;

import com.imwj.mybatis.builder.BaseBuilder;
import com.imwj.mybatis.mapping.MappedStatement;
import com.imwj.mybatis.mapping.SqlCommandType;
import com.imwj.mybatis.mapping.SqlSource;
import com.imwj.mybatis.scripting.LanguageDriver;
import com.imwj.mybatis.session.Configuration;
import org.dom4j.Element;

import javax.crypto.Cipher;
import java.util.Locale;

/**
 * @author wj
 * @create 2023-08-18 11:38
 * @description XML语句构建器
 */
public class XMLStatementBuilder extends BaseBuilder {

    private String currentNamespace;
    private Element element;

    public XMLStatementBuilder(Configuration configuration, Element element, String currentNamespace) {
        super(configuration);
        this.element = element;
        this.currentNamespace = currentNamespace;
    }

    /**
     * 解析sql语句(select|insert|update|delete)
     */
    public void parseStatementNode() {
        String id = element.attributeValue("id");
        // 参数类型
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveAlias(parameterType);
        // 结果类型
        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveAlias(resultType);
        // 命令类型(select|insert|update|delete)
        String nodeName = element.getName();
        SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));

        // 获取默认语言驱动器
        Class<?> langClass = configuration.getLanguageRegistry().getDefaultDriverClass();
        LanguageDriver languageDriver = configuration.getLanguageRegistry().getDriver(langClass);

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, element, parameterTypeClass);

        MappedStatement mappedStatement = new MappedStatement.Builder(configuration, currentNamespace + "." + id, sqlCommandType, sqlSource, resultTypeClass).build();

        // 添加解析 SQL
        configuration.addMappedStatement(mappedStatement);
    }
}
