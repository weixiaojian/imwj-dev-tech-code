package com.imwj.mybatis.session;

import com.imwj.mybatis.binding.MapperRegistry;
import com.imwj.mybatis.datasource.druid.DruidDataSourceFactory;
import com.imwj.mybatis.datasource.pooled.PooledDataSourceFactory;
import com.imwj.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.imwj.mybatis.executor.Executor;
import com.imwj.mybatis.executor.SimpleExecutor;
import com.imwj.mybatis.executor.parameter.ParameterHandler;
import com.imwj.mybatis.executor.resultset.DefaultResultSetHandler;
import com.imwj.mybatis.executor.resultset.ResultSetHandler;
import com.imwj.mybatis.executor.statement.PreparedStatementHandler;
import com.imwj.mybatis.executor.statement.StatementHandler;
import com.imwj.mybatis.mapping.BoundSql;
import com.imwj.mybatis.mapping.Environment;
import com.imwj.mybatis.mapping.MappedStatement;
import com.imwj.mybatis.reflection.MetaObject;
import com.imwj.mybatis.reflection.factory.DefaultObjectFactory;
import com.imwj.mybatis.reflection.factory.ObjectFactory;
import com.imwj.mybatis.reflection.wrapper.DefaultObjectWrapperFactory;
import com.imwj.mybatis.reflection.wrapper.ObjectWrapperFactory;
import com.imwj.mybatis.scripting.LanguageDriver;
import com.imwj.mybatis.scripting.LanguageDriverRegistry;
import com.imwj.mybatis.scripting.xmltags.XMLLanguageDriver;
import com.imwj.mybatis.transaction.Transaction;
import com.imwj.mybatis.transaction.jdbc.JdbcTransactionFactory;
import com.imwj.mybatis.type.TypeAliasRegistry;
import com.imwj.mybatis.type.TypeHandlerRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wj
 * @create 2023-07-20 17:41
 * @description 配置项
 */
public class Configuration {

    /**
     * 环境
     */
    protected Environment environment;
    /**
     * 映射注册机
     */
    protected MapperRegistry mapperRegistry = new MapperRegistry(this);
    /**
     * 映射的语句，存在Map里
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();
    /**
     * 类型别名注册机
     */
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();

    // 类型处理器注册机
    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

    // 对象工厂和对象包装器工厂
    protected ObjectFactory objectFactory = new DefaultObjectFactory();
    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

    protected final Set<String> loadedResources = new HashSet<>();

    protected String databaseId;

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);

        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);

        languageRegistry.setDefaultDriverClass(XMLLanguageDriver.class);
    }

    public void addMappers(String packageName) {
        mapperRegistry.addMapper(packageName);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    public String getDatabaseId() {
        return databaseId;
    }

    /**
     * 创建结果处理器
     */
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql){
        return new DefaultResultSetHandler(executor, mappedStatement, boundSql);
    }

    /**
     * 生产执行器
     */
    public Executor newExecutor(Transaction transaction) {
        return new SimpleExecutor(this, transaction);
    }

    /**
     * 创建语句处理器
     */
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        return new PreparedStatementHandler(executor, mappedStatement, parameter, resultHandler, boundSql);
    }

    // 创建元对象
    public MetaObject newMetaObject(Object object) {
        return MetaObject.forObject(object, objectFactory, objectWrapperFactory);
    }

    // 类型处理器注册机
    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }

    public void addLoadedResource(String resource) {
        loadedResources.add(resource);
    }

    public LanguageDriverRegistry getLanguageRegistry() {
        return languageRegistry;
    }

    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        // 创建参数处理器
        ParameterHandler parameterHandler = mappedStatement.getLang().createParameterHandler(mappedStatement, parameterObject, boundSql);
        // 插件的一些参数，也是在这里处理，暂时不添加这部分内容 interceptorChain.pluginAll(parameterHandler);
        return parameterHandler;
    }


    public LanguageDriver getDefaultScriptingLanguageInstance() {
        return languageRegistry.getDefaultDriver();
    }

}
