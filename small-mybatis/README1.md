# 第四章：数据源的解析、创建和使用
![image](https://github.com/weixiaojian/study-code/blob/master/small-mybatis/img/mybatis-04.png?raw=true)
* 1.创建事务接口`Transaction`和事务工厂`TransactionFactory`用于保存事务信息
```
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(dataSource, level, autoCommit);
    }
}

public class JdbcTransaction implements Transaction {

    protected Connection connection;
    protected DataSource dataSource;
    protected TransactionIsolationLevel level = TransactionIsolationLevel.NONE;
    protected boolean autoCommit;

    public JdbcTransaction(Connection connection) {
        this.connection = connection;
    }

    public JdbcTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit){
        this.dataSource = dataSource;
        this.level = level;
        this.autoCommit = autoCommit;
    }

    @Override
    public Connection getConnection() throws SQLException {
        connection = dataSource.getConnection();
        connection.setTransactionIsolation(level.getLevel());
        connection.setAutoCommit(autoCommit);
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.close();
        }
    }
}
```
* 2.创建`DataSourceFactory`接口和`DruidDataSourceFactory`实现，用于构建数据源
```
public interface DataSourceFactory {

    void setProperties(Properties props);

    DataSource getDataSource();
}

public class DruidDataSourceFactory implements DataSourceFactory {

    private Properties props;

    @Override
    public void setProperties(Properties props) {
        this.props = props;
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(props.getProperty("driver"));
        dataSource.setUrl(props.getProperty("url"));
        dataSource.setUsername(props.getProperty("username"));
        dataSource.setPassword(props.getProperty("password"));
        return dataSource;
    }
}
```
* 3.创建基础注册器`TypeAliasRegistry`内部使用map存储
```
public class TypeAliasRegistry {

    private final Map<String, Class<?>> TYPE_ALIASES = new HashMap<>();

    public TypeAliasRegistry() {
        // 构造函数里注册系统内置的类型别名
        registerAlias("string", String.class);

        // 基本包装类型
        registerAlias("byte", Byte.class);
        registerAlias("long", Long.class);
        registerAlias("short", Short.class);
        registerAlias("int", Integer.class);
        registerAlias("integer", Integer.class);
        registerAlias("double", Double.class);
        registerAlias("float", Float.class);
        registerAlias("boolean", Boolean.class);
    }

    public void registerAlias(String alias, Class<?> value) {
        String key = alias.toLowerCase(Locale.ENGLISH);
        TYPE_ALIASES.put(key, value);
    }

    public <T> Class<T> resolveAlias(String string) {
        String key = string.toLowerCase(Locale.ENGLISH);
        return (Class<T>) TYPE_ALIASES.get(key);
    }
}
```
* 4.`configuration`初始化时加载默认的`JdbcTransactionFactory`、`DruidDataSourceFactory`
```
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

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
    }
}    
```

* 5.在`XmlConfigBuilder#environmentsElement`方法中解析数据库连接配置、构建事务和数据源`JdbcTransactionFactory`、`DruidDataSourceFactory`，并将这些信息带到`configuration`中
```
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
    .....
}
```
* 6.最后在`DefaultSqlSession`执行对应sql语句时获取连接并执行查询将结果封装
```
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) throws SQLException, ClassNotFoundException {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        Environment environment = configuration.getEnvironment();
        Connection connection = environment.getDataSource().getConnection();

        BoundSql boundSql = mappedStatement.getBoundSql();
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
        preparedStatement.setLong(1, Long.parseLong(((Object[]) parameter)[0].toString()));
        ResultSet resultSet = preparedStatement.executeQuery();

        List<T> objList = resultSet2Obj(resultSet, Class.forName(boundSql.getResultType()));
        return objList.get(0);
    }
    
    /**
     * 查询结构封装为指的类
     * @param resultSet
     * @param clazz
     * @return
     * @param <T>
     */
    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 每次遍历行值
            while (resultSet.next()) {
                T obj = (T) clazz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Method method;
                    if (value instanceof Timestamp) {
                        method = clazz.getMethod(setMethod, Date.class);
                    } else {
                        method = clazz.getMethod(setMethod, value.getClass());
                    }
                    method.invoke(obj, value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}    
```
* 7.总结
* 此章节增加了事务接口`Transaction`和事务工厂`TransactionFactory`用于保存事务信息（DefaultSqlSession中还未启用）
* 增加了DataSourceFactory`接口和`DruidDataSourceFactory`实现，用于构建数据源，在DefaultSqlSession中真正的查询数据库
* 增加了DefaultSqlSession实际查询了数据库并将查询结果封装返回
* 流程梳理：`xmlConfigBuilder.parse()`加载xml配置文件到`configuration`(增加了事务和数据源配置) > `SqlSessionFactoryBuilder`创建`DefaultSqlSessionFactory`创建`DefaultSqlSession` > `getMapper`获取到代理类 > 执行代理类中的`selectOne`方法

# 第五章：无池化和有池化实现
![image](https://github.com/weixiaojian/study-code/blob/master/small-mybatis/img/mybatis-05.png?raw=true)
* 1.无池化实现：`UnpooledDataSourceFactory`主要是将url等赋值 并用于创建`UnpooledDataSource`，后者实现了`DataSource`接口提供了获取数据库连接的方法（注册和管理 JDBC 驱动程序、驱动代理、初始化驱动、获取连接）
```
com.imwj.mybatis.datasource.unpooled.UnpooledDataSource
com.imwj.mybatis.datasource.unpooled.UnpooledDataSourceFactory
```
* 2.有池化实现：`PooledDataSourceFactory`池工厂、`PooledDataSource`池化数据源、`PooledConnection`有池化代理连接
```
com.imwj.mybatis.datasource.unpooled.PooledDataSource
com.imwj.mybatis.datasource.unpooled.PooledDataSourceFactory
com.imwj.mybatis.datasource.unpooled.PooledConnection
```
* 3.总结：本章主要新增了无池化连接和有池化连接，`Configuration`中增加`UNPOOLED`、`POOLED`注册机，xml配置文件中更改`dataSourceType`即可

# 第六章：SQL执行器的定义和实现
* 0.SQL执行器包含：参数处理器`statement`、结果处理器`resultset`、sql执行器`Executor`三大部分(三者均在`configuration`中创建)，此部分功能设计均采用了模板方法设计模式（一个接口、一个抽象类、多个实现类）
* 1.参数处理器包含：简单参数处理器`SimpleStatementHandler`、预语句处理器`PreparedStatementHandler`(主要使用)，
```
com.imwj.mybatis.executor.statement.PreparedStatementHandler
com.imwj.mybatis.executor.statement.SimpleStatementHandler
```
* 2.结果处理器`DefaultResultSetHandler`负责将查询结果转换为指的实体
```
com.imwj.mybatis.executor.resultset.DefaultResultSetHandler
```
* 3.SQL执行器`SimpleExecutor`同样试用模板方法设计，主要是控制数据库事务和创建连接 并执行sql语句，并将`DefaultSqlSession`中的selectOne方法替换为执行器
```
com.imwj.mybatis.executor.SimpleExecutor
```
* 总结：本章节主要是更换了`DefaultSqlSession`中的selectOne方法替换为执行器（业务解耦），并在执行器中调用了预语句处理器`PreparedStatementHandler`和结果处理器`DefaultResultSetHandler`，最后将执行结果返回

# 第七章：把反射用到出神入化
* 对于属性的获取从硬编码的方式调整为通过反射来获取和填充
```
1.比如：配置文件中的url、name、password等赋值到dataSource中之前是通过
    PooledDataSource pooledDataSource = new PooledDataSource();
    pooledDataSource.setDriver(props.getProperty("driver"));
    pooledDataSource.setUrl(props.getProperty("url"));
    pooledDataSource.setUsername(props.getProperty("username"));
    pooledDataSource.setPassword(props.getProperty("password"));
    
2.优化后：我们可以直接通过MetaObject来读取和填充值
    public void setProperties(Properties props) {
        // 1.先加载指定对象
        MetaObject metaObject = SystemMetaObject.forObject(dataSource);
        // 2.循环xml配置值
        for (Object key : props.keySet()) {
            String propertyName = (String) key;
            // 3.两者属性一致(有对应的set方法)就进行天才
            if (metaObject.hasSetter(propertyName)) {
                String value = (String) props.get(propertyName);
                Object convertedValue = convertValue(metaObject, propertyName, value);
                metaObject.setValue(propertyName, convertedValue);
            }
        }
    }
```

# 第八章：细化XML语句构建器，完善静态SQL解析
* 通过设计原则进行拆分和解耦，运用不同的类来承担不同的职责：映射构建器（XMLMapperBuilder）、语句构建器（XMLStatementBuilder）、源码构建器（SqlSourceBuilder）

# 第九章：使用策略模式，调用参数处理器
* 通过策略解耦，模板定义流程将mybatis参数处理模块化，针对不同类型的参数使用了对应的类型处理器
* 1.`MapperProxy.invoke`代理方法变更为`mapperMethod.execute`，同时此处对mapper方法进行了缓存处理
```
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Object的toString()等相关方法不需要代理  直接执行
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            final MapperMethod mapperMethod = cachedMapperMethod(method);
            return mapperMethod.execute(sqlSession, args);
        }
    }

    /**
     * 去缓存中找MapperMethod
     */
    private MapperMethod cachedMapperMethod(Method method) {
        MapperMethod mapperMethod = methodCache.get(method);
        if (mapperMethod == null) {
            //找不到才去new
            mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
            methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }
```
* 2.`MapperMethod`映射器方法中对增删改查做了不同处理，对请求参数做了签名处理`com.imwj.mybatis.binding.MapperMethod`
```
    public Object execute(SqlSession sqlSession, Object[] args) throws SQLException, ClassNotFoundException {
        Object result = null;
        switch (command.getType()) {
            case INSERT:
                break;
            case DELETE:
                break;
            case UPDATE:
                break;
            case SELECT:
                Object param = method.convertArgsToSqlCommandParam(args);
                result = sqlSession.selectOne(command.getName(), param);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command.getName());
        }
        return result;
    }
```
* 3.最后在`DefaultParameterHandler.setParameters`将之前封装的参数取出并一一对应
```
    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (null != parameterMappings) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                String propertyName = parameterMapping.getProperty();
                Object value;
                if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    // 通过 MetaObject.getValue 反射取得值设进去
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    value = metaObject.getValue(propertyName);
                }
                JdbcType jdbcType = parameterMapping.getJdbcType();

                // 设置参数
                logger.info("根据每个ParameterMapping中的TypeHandler设置对应的参数信息 value：{}", JSON.toJSONString(value));
                TypeHandler typeHandler = parameterMapping.getTypeHandler();
                typeHandler.setParameter(ps, i + 1, value, jdbcType);
            }
        }
    }
```