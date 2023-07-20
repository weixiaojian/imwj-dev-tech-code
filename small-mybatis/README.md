# 手写mybatis

* 地址：[小博哥-手写mybatis专栏](https://bugstack.cn/md/spring/develop-mybatis/2022-03-20-第1章：开篇介绍，手写Mybatis能给你带来什么？.html)

# 第一章：创建简单的映射器代理工厂
![image](https://www.yinxiang.com/blog/wp-content/uploads/2018/07/%E5%94%AE%E7%A5%A8%E5%BE%AE%E4%BF%A1%E5%B0%81%E9%9D%A22.png)
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
* 总体流程梳理：测试方法中定义好接口和sqlSession作为参数传入 > `factory.newInstance`创建一个代理类 > 代理执行`queryUserName`方法时其实是执行`MapperProxy.invoke`的代理方法

# 第二章：实现映射器的注册和使用
![image](https://www.yinxiang.com/blog/wp-content/uploads/2018/07/%E5%94%AE%E7%A5%A8%E5%BE%AE%E4%BF%A1%E5%B0%81%E9%9D%A22.png)
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