# 手写mybatis

* 地址：[小博哥-手写mybatis专栏](https://bugstack.cn/md/spring/develop-mybatis/2022-03-20-第1章：开篇介绍，手写Mybatis能给你带来什么？.html)

# 第一章：创建简单的映射器代理工厂
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