package com.imwj.mybatis.builder;

import com.imwj.mybatis.session.Configuration;

/**
 * @author wj
 * @create 2023-07-20 17:40
 * @description 构建器的基类，建造者模式(xml、yml、txt)
 */
public abstract class BaseBuilder {

    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
