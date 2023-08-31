package com.imwj.mybatis.scripting.xmltags;

import com.imwj.mybatis.executor.parameter.ParameterHandler;
import com.imwj.mybatis.mapping.BoundSql;
import com.imwj.mybatis.mapping.MappedStatement;
import com.imwj.mybatis.mapping.SqlSource;
import com.imwj.mybatis.scripting.LanguageDriver;
import com.imwj.mybatis.scripting.defaults.DefaultParameterHandler;
import com.imwj.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * @author wj
 * @create 2023-08-18 16:18
 * @description XML语言驱动器
 */
public class XMLLanguageDriver implements LanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType) {
        // 用XML脚本构建器解析
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
    }
}
