package com.imwj.mybatis.builder;

import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.type.TypeAliasRegistry;
import com.imwj.mybatis.type.TypeHandlerRegistry;

/**
 * @author wj
 * @create 2023-07-20 17:40
 * @description 构建器的基类，建造者模式(xml、yml、txt)
 */
public abstract class BaseBuilder {

    protected final Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;
    protected final TypeHandlerRegistry typeHandlerRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
        this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    protected Class<?> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }
}
