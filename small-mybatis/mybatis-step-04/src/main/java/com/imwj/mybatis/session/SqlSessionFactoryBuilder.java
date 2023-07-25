package com.imwj.mybatis.session;

import com.imwj.mybatis.builder.xml.XmlConfigBuilder;
import com.imwj.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * @author wj
 * @create 2023-07-20 17:33
 * @description SqlSessionFactory建造者工厂
 */
public class SqlSessionFactoryBuilder {

    /**
     * 根据指定路径创建SqlSessionFactory
     * @param reader
     * @return
     */
    public SqlSessionFactory build(Reader reader){
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    /**
     * 根据指定配置创建SqlSessionFactory
     * @param config
     * @return
     */
    public SqlSessionFactory build(Configuration config){
        return new DefaultSqlSessionFactory(config);
    }

}
