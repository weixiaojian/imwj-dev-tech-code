# 手写mybatis 

* 地址：[小博哥-手写mybatis专栏](https://bugstack.cn/md/spring/develop-mybatis/2022-03-20-第1章：开篇介绍，手写Mybatis能给你带来什么？.html)

# 第一章：创建简单的映射器代理工厂
![image](https://github.com/weixiaojian/study-code/blob/master/small-mybatis/img/mybatis-01.png?raw=true)
* 1.创建一个映射代理器`MapperProxy`实现`InvocationHandler`（jdk动态代理接口），
```
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -6424540398559729838L;

    /**
     * sqlSession存储器
     */
    private Map<String,String> sqlSession;
    /**
     * 代理类
     */
    private final Class<T> mapperInterface;

    public MapperProxy(Map<String, String> sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    /**
     * 执行代理方法
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Object的toString()等相关方法不需要代理  直接执行
        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this, args);
        }else{
            return "你的被代理了！" + sqlSession.get(mapperInterface.getName() + "." + method.getName());
        }
    }
}
```
* 2.创建一个映射代理器`MapperProxyFactory`，该类的主要操作是用于把代理的创建给封装 无需每次都调用`Proxy.newProxyInstance`
```
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * 获取代理对象
     * @param sqlSession
     * @return
     */
    public T newInstance(Map<String, String> sqlSession){
        final MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession, mapperInterface);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
```
* 3.测试：创建一个`IUserDao`接口，一个测试类如下
```
    @Test
    public void test_MapperProxyFactory() {
        MapperProxyFactory<IUserDao> factory = new MapperProxyFactory<>(IUserDao.class);
        Map<String, String> sqlSession = new HashMap<>();

        sqlSession.put("com.imwj.mybatis.test.IUserDao.queryUserName", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户姓名");
        sqlSession.put("com.imwj.mybatis.test.IUserDao.queryUserAge", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户年龄");
        IUserDao userDao = factory.newInstance(sqlSession);

        String res = userDao.queryUserName("10001");
        logger.info("测试结果：{}", res);
    }
```
* 总结：
* 此章节就是创建了代理`MapperProxy`、代理器工厂`MapperProxyFactory`，使用了工厂模式
* 流程梳理：测试方法中定义好接口和sqlSession作为参数传入 > `factory.newInstance`创建一个代理类 > 代理执行`queryUserName`方法时其实是执行`MapperProxy.invoke`的代理方法



# 第二章：实现映射器的注册和使用
![image](https://github.com/weixiaojian/study-code/blob/master/small-mybatis/img/mybatis-02.png?raw=true)
* 1.创建一个`MapperRegistry`映射器注册类，该类主要用于加载指定目录的
```
public class MapperRegistry {

    /**
     * 将已添加映射器代理加入到HashMap
     */
    private final Map<Class<?>, MapperProxyFactory> knownMappers = new HashMap<>();

    public <T> T getMapper(Class<T> type, SqlSession sqlSession){
        final MapperProxyFactory<T> mapperProxyFactory =  (MapperProxyFactory<T>) knownMappers.get(type);
        if(mapperProxyFactory == null){
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        }catch (Exception e){
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    /**
     * 加载指定路径的接口
     * @param type
     * @param <T>
     */
    public <T> void addMapper(Class<T>  type){
        /* Mapper 必须是接口才会注册 */
        if(type.isInterface()){
            // 不允许重复添加
            if(knownMappers.containsKey(type)){
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            // 注册映射器代理工厂
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    /**
     * 加载指定包下的所有接口
     * @param packageName
     */
    public void addMapper(String packageName){
        Set<Class<?>> mapperSet = ClassScanner.scanPackage(packageName);
        for(Class<?> mapperClass : mapperSet){
            addMapper(mapperClass);
        }
    }
}
```
* 2.创建一个默认的`DefaultSqlSessionFactory`，该类根据`MapperRegistry`来创建`DefaultSqlSession`
```
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }

}
```

* 3.创建默认的`DefaultSqlSession`，主要用于获取代理类和提供select方法
```
public class DefaultSqlSession implements SqlSession {

    /**
     * 映射器注册机
     */
    private MapperRegistry mapperRegistry;

    public DefaultSqlSession(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        System.out.println("执行selectOne，参数：" + parameter);
        return (T) "test";
    }

    /**
     * 获取代理类
     * @param type Mapper interface class
     * @return
     * @param <T>
     */
    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }
}
```
* 4.测试使用
```
    @Test
    public void test1(){
        // 1.注册Mapper
        MapperRegistry registry = new MapperRegistry();
        registry.addMapper("com.imwj.mybatis.test.dao");

        // 2.从SqlSession工厂获取session
        DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3.获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        String res  = userDao.queryUserName("10001");
        System.out.println(res);
    }
```
* 总结：
* 增加了`MapperRegistry`映射器注册类，用于统一管理指的目录下需要代理的类
* 增加了`DefaultSqlSessionFactory`、`DefaultSqlSession`用于执行sql查询
* 流程梳理：`MapperRegistry`加载指的目录下的所有类 > 创建`DefaultSqlSessionFactory`用于创建`DefaultSqlSession` > 其中的`getMapper`方法用于获取代理类 > 执行代理类中的`selectOne`方法

# 第三章：Mapper XML的解析和注册使用
![image](https://github.com/weixiaojian/study-code/blob/master/small-mybatis/img/mybatis-03.png?raw=true)
* 1.新建`XmlConfigBuilder`xml配置读取，用于解析resources中的`mybatis-config-datasource.xml`和`User_Mapper.xml`，将其中的配置封装为`Configuration`和`MappedStatement`
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
            mapperElement(root.element("mappers"));
        }catch (Exception e){
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
        return configuration;
    }

    /**
     * 解析指定标签mapper
     * @param mappers
     */
    private void mapperElement(Element mappers) throws Exception {
        List<Element> mapperList = mappers.elements("mapper");
        for(Element e : mapperList){
            // 1.得到resource标签，解析User_Mapper.xml
            String resource = e.attributeValue("resource");
            Reader reader = Resources.getResourceAsReader(resource);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(reader));
            Element root = document.getRootElement();
            // 2.命名空间
            String namespace = root.attributeValue("namespace");

            // 3.SELECT
            List<Element> selectNodes = root.elements("select");
            for (Element node : selectNodes) {
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                // ? 匹配
                Map<Integer, String> parameter = new HashMap<>();
                Pattern pattern = Pattern.compile("(#\\{(.*?)})");
                Matcher matcher = pattern.matcher(sql);
                for (int i = 1; matcher.find(); i++) {
                    String g1 = matcher.group(1);
                    String g2 = matcher.group(2);
                    parameter.put(i, g2);
                    sql = sql.replace(g1, "?");
                }

                String msId = namespace + "." + id;
                String nodeName = node.getName();
                SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
                MappedStatement mappedStatement = new MappedStatement.Builder(configuration, msId, sqlCommandType, parameterType, resultType, sql, parameter).build();
                // 添加解析 SQL
                configuration.addMappedStatement(mappedStatement);
            }
            // 注册Mapper映射器
            configuration.addMapper(Resources.classForName(namespace));
        }
    }
}

public class Configuration {

    /**
     * 映射注册机
     */
    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    /**
     * 映射的语句，存在Map里
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

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
}
```
* 2.创建`SqlSessionFactoryBuilder`，此类是加载配置的入口：编译上面的`XmlConfigBuilder`，并将生成的`Configuration`封装到`DefaultSqlSessionFactory`中
```
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
```
* 3.通过`SqlSessionFactoryBuilder`得到`SqlSessionFactory`并生成`SqlSession`，核心点就是不断将`Configuration`传递
```
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
```
* 4.通过`sqlSession.getMapper`方法即`mapperRegistry.getMapper`动态代理生成接口代理类，此处会将当前`DefaultSqlSession`作为参数一直传递下去
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
    public <T> T selectOne(String statement, Object parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        return (T) ("你被代理了！" + "\n方法：" + statement + "\n入参：" + parameter + "\n待执行SQL：" + mappedStatement.getSql());
    }

    /**
     * 获取代理类
     * @param type Mapper interface class
     * @return
     * @param <T>
     */
    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
```
* 5.测试使用
```
    @Test
    public void test1() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        String res = userDao.queryUserInfoById("10001");
        logger.info("测试结果：{}", res);
    }
```
* 总结：
* 增加了`Configuration`用于存储映射注册机、映射的语句等，从最初的xml解析数据赋值后一直带到`DefaultSqlSession`执行语句
* 增加了`XmlConfigBuilder`用于解析xml配置文件中的sql语句
* 增加了`SqlSessionFactoryBuilder`用于构建`DefaultSqlSessionFactory`
* 流程梳理：`xmlConfigBuilder.parse()`加载xml配置文件到`configuration` > `SqlSessionFactoryBuilder`创建`DefaultSqlSessionFactory`创建`DefaultSqlSession` > `getMapper`获取到代理类 > 执行代理类中的`selectOne`方法