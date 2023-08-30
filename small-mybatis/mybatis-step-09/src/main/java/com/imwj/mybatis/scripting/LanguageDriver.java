package com.imwj.mybatis.scripting;

import com.imwj.mybatis.executor.parameter.ParameterHandler;
import com.imwj.mybatis.mapping.BoundSql;
import com.imwj.mybatis.mapping.MappedStatement;
import com.imwj.mybatis.mapping.SqlSource;
import com.imwj.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * @author wj
 * @create 2023-08-18 16:03
 * @description 脚本语言驱动
 */
public interface LanguageDriver {

    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);


    /**
     * 创建参数处理器
     */
    ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);
}
