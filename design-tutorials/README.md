> 本文主要是对Java中一些常用的设计模式进行讲解 后期会进行不断的更新，欢迎浏览

# 23种设计模式
* 创建型模式，共五种：工厂方法模式、抽象工厂模式、建造者模式、原型模式、单例模式。
* 结构型模式，共七种：适配器模式、桥接模式、组合模式、装饰器模式、外观模式、享元模式、代理模式。
* 行为型模式，共十一种：责任链模式、命令模式、迭代器模式、中介者模式、备忘录模式、观察者模式、状态模式、策略模式、模板方法模式、访问者模式、解释器模式。

# 六大设计原则
## 单一职责原则
* 强调一个类或模块应该只负责一项功能或职责（高内聚 低耦合）
## 开闭原则
* 在设计和构建软件实体时，应该尽量通过扩展(继承)现有的代码来实现新的功能，而不是修改已有的代码
## 里氏替原则
* 里氏替换原则要求子类能够替换父类并在不影响程序正确性的前提下扩展父类的功能。这意味着子类应该保持父类的行为和约束，并且可以在不违反父类定义的前提下进行功能扩展。
* 子类必须实现父类的所有抽象方法。
* 子类可以有自己的个性化方法，但不能修改父类已有的方法。
* 子类的前置条件（输入参数）要比父类的更宽松。
* 子类的后置条件（输出结果）要比父类的更严格。
* 子类不能抛出比父类更多或更宽泛的异常。
## 迪米特法则
* 一个对象应该对其他对象有尽可能少的了解。具体而言，一个对象应该尽量减少对其他对象的依赖，只与直接的朋友通信，不与陌生的对象通信。
## 接口隔离原则
* 通过合理设计接口，使接口的使用者不依赖于它们不需要的方法（允许实现多个接口）
## 依赖倒置原则
* 强调高层模块不应该依赖于低层模块的具体实现，而是应该依赖于抽象接口。同时，抽象不应该依赖于具体实现，具体实现应该依赖于抽象。


# 创建型模式
## 工厂方法模式(重点)
* 在父类中提供一个创建对象的方法， 允许子类决定实例化对象的类型
* 1.新建一个`ICommodity`接口，他拥有三个实现类`CardCommodityService、CouponCommodityService、GoodsCommodityService`
* 2.创建一个统一的工厂类`StoreFactory`，用工厂类去创建上面的三个业务对象
```
public class StoreFactory {

    private Map<Integer, ICommodity> factoryMap = new HashMap<>();

    /**
     * 工厂初始化
     */
    {
        factoryMap.put(1, new CouponCommodityService());
        factoryMap.put(2, new GoodsCommodityService());
        factoryMap.put(3, new CardCommodityService());
    }


    /**
     * 奖品类型方式实例化
     * @param commodityType 奖品类型
     * @return              实例化对象
     */
    public ICommodity getCommodityService(Integer commodityType) {
        if (null == commodityType) return null;
        ICommodity iCommodity = factoryMap.get(commodityType);
        if(iCommodity != null){
            return iCommodity;
        }
        throw new RuntimeException("不存在的奖品服务类型");
    }

    /**
     * 奖品类信息方式实例化
     * @param clazz 奖品类
     * @return      实例化对象
     */
    public ICommodity getCommodityService(Class<? extends ICommodity> clazz) throws IllegalAccessException, InstantiationException {
        if (null == clazz) return null;
        return clazz.newInstance();
    }
}

```
* 3.使用时通过工厂类来创建对象
```
    @Test
    public void test_awardToUser() throws Exception {
        // 1. 优惠券
        ICommodity commodityService_1 = storeFactory.getCommodityService(1);
        commodityService_1.sendCommodity("10001", "EGM1023938910232121323432", "791098764902132", null);

        // 2. 实物商品
        ICommodity commodityService_2 = storeFactory.getCommodityService(GoodsCommodityService.class);
        commodityService_2.sendCommodity("10001", "9820198721311", "1023000020112221113", new HashMap<String, String>() {{
            put("consigneeUserName", "谢飞机");
            put("consigneeUserPhone", "15200292123");
            put("consigneeUserAddress", "吉林省.长春市.双阳区.XX街道.檀溪苑小区.#18-2109");
        }});

        // 3. 第三方兑换卡(模拟爱奇艺)
        ICommodity commodityService_3 = storeFactory.getCommodityService(3);
        commodityService_3.sendCommodity("10001", "AQY1xjkUodl8LO975GdfrYUio", null, null);
    }
```

## 抽象工厂模式
* 抽象工厂模式与工厂方法模式虽然主要意图都是为了解决，接口选择问题。但在实现上，抽象工厂是一个中心工厂，创建其他工厂的模式。
![抽象工厂模式](http://blog.imwj.club//upload/2023/06/01ipqo1sachrdpnpbg965mbpl7.png)
* 1.`ICacheAdapter`接口下有`EGMCacheAdapter、IIRCacheAdapter`两个服务实现类
* 2.`InvocationHandler`是针对`ICacheAdapter`接口的动态代理类
```
public class JDKInvocationHandler implements InvocationHandler {

    private ICacheAdapter cacheAdapter;

    public JDKInvocationHandler(ICacheAdapter cacheAdapter) {
        this.cacheAdapter = cacheAdapter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 获取处理的指定方法
        return ICacheAdapter.class.getMethod(method.getName(), ClassLoaderUtils.getClazzByArgs(args)).invoke(cacheAdapter, args);
    }
}
```
* 3.抽象工厂创建代理类
```
public class JDKProxyFactory {

    public static <T> T getProxy(Class<T> cacheClazz, Class<? extends ICacheAdapter> cacheAdapter) throws Exception {
        InvocationHandler handler = new JDKInvocationHandler(cacheAdapter.newInstance());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 动态代理方法：Proxy.newProxyInstance(类加载器，需要代理的接口，执行handler.invoke方法)
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{cacheClazz}, handler);
    }
}
```
* 4.测试使用
```
    @Test
    public void test_cacheService() throws Exception {
        CacheService proxy_Gm = JDKProxyFactory.getProxy(CacheService.class, EGMCacheAdapter.class);
        proxy_Gm.set("user_name_01", "imwj");
        String val01 = proxy_Gm.get("user_name_01");
        logger.info("缓存服务 EGM 测试，proxy_EGM.get 测试结果：{}", val01);

        CacheService proxy_IIR = JDKProxyFactory.getProxy(CacheService.class, IIRCacheAdapter.class);
        proxy_IIR.set("user_name_01", "imwj");
        String val02 = proxy_IIR.get("user_name_01");
        logger.info("缓存服务 IIR 测试，proxy_IIR.get 测试结果：{}", val02);
    }
```
## 建造者模式
* 通过将多个简单对象通过一步步的组装构建出一个复杂对象的过程。
* 1.建造模式接口
```
public interface IMenu {


    /**
     * 吊顶
     */
    IMenu appendCeiling(Matter matter);

    /**
     * 涂料
     */
    IMenu appendCoat(Matter matter);

    /**
     * 地板
     */
    IMenu appendFloor(Matter matter);

    /**
     * 地砖
     */
    IMenu appendTile(Matter matter);

    /**
     * 明细
     */
    String getDetail();
}
```
* 2.接口实现类
```
public class DecorationPackageMenu implements IMenu{


    private List<Matter> list = new ArrayList<Matter>();  // 装修清单
    private BigDecimal price = BigDecimal.ZERO;      // 装修价格

    private BigDecimal area;  // 面积
    private String grade;     // 装修等级；豪华欧式、轻奢田园、现代简约

    private DecorationPackageMenu() {
    }

    public DecorationPackageMenu(Double area, String grade) {
        this.area = new BigDecimal(area);
        this.grade = grade;
    }


    @Override
    public IMenu appendCeiling(Matter matter) {
        list.add(matter);
        price = price.add(area.multiply(new BigDecimal("0.2")).multiply(matter.price()));
        return this;
    }

    @Override
    public IMenu appendCoat(Matter matter) {
        list.add(matter);
        price = price.add(area.multiply(new BigDecimal("1.4")).multiply(matter.price()));
        return this;
    }

    public IMenu appendFloor(Matter matter) {
        list.add(matter);
        price = price.add(area.multiply(matter.price()));
        return this;
    }

    public IMenu appendTile(Matter matter) {
        list.add(matter);
        price = price.add(area.multiply(matter.price()));
        return this;
    }

    public String getDetail() {

        StringBuilder detail = new StringBuilder("\r\n-------------------------------------------------------\r\n" +
                "装修清单" + "\r\n" +
                "套餐等级：" + grade + "\r\n" +
                "套餐价格：" + price.setScale(2, BigDecimal.ROUND_HALF_UP) + " 元\r\n" +
                "房屋面积：" + area.doubleValue() + " 平米\r\n" +
                "材料清单：\r\n");

        for (Matter matter: list) {
            detail.append(matter.scene()).append("：").append(matter.brand()).append("、").append(matter.model()).append("、平米价格：").append(matter.price()).append(" 元。\n");
        }
        return detail.toString();
    }
}
```
* 3.建造模式构造器
```
public class Builder {

    public IMenu levelOne(Double area) {
        return new DecorationPackageMenu(area, "豪华欧式")
                .appendCeiling(new LevelTwoCeiling())    // 吊顶，二级顶
                .appendCoat(new DuluxCoat())             // 涂料，多乐士
                .appendFloor(new ShengXiangFloor());     // 地板，圣象
    }

    public IMenu levelTwo(Double area){
        return new DecorationPackageMenu(area, "轻奢田园")
                .appendCeiling(new LevelTwoCeiling())   // 吊顶，二级顶
                .appendCoat(new LiBangCoat())           // 涂料，立邦
                .appendTile(new MarcoPoloTile());       // 地砖，马可波罗
    }

    public IMenu levelThree(Double area){
        return new DecorationPackageMenu(area, "现代简约")
                .appendCeiling(new LevelOneCeiling())   // 吊顶，一级顶
                .appendCoat(new LiBangCoat())           // 涂料，立邦
                .appendTile(new DongPengTile());        // 地砖，东鹏
    }

}
```

* 4.测试使用
```
    @Test
    public void test_Builder(){
        IMenu one = builder.levelOne(100D);
        System.out.println(one.getDetail());

        IMenu two = builder.levelTwo(100D);
        System.out.println(two.getDetail());

        IMenu three = builder.levelThree(100D);
        System.out.println(three.getDetail());

    }
```
## 原型模式
* 主要解决的问题就是创建重复对象，而这部分对象内容本身比较复杂，生成过程可能从库或者RPC接口中获取数据的耗时较长，因此采用克隆的方式节省时间
* 使用频率不高 可以参考`step04-00`相关代码
## 单例模式
* 单例对象的类只能允许一个实例存在
* 0.静态类
```
public class Singleton_00 {

    private static Map<String, String> cache = new ConcurrentHashMap<String, String>();

}
```
* 1.懒汉模式(线程不安全)
```
public class Singleton_01 {

    private static Singleton_01 singleton_01;

    private Singleton_01() {
    }
    public static Singleton_01 getSingleton_01() {
        if (singleton_01 == null) {
            singleton_01 = new Singleton_01();
        }
        return singleton_01;
    }
}
```
* 2.懒汉模式(线程安全)
```
public class Singleton_02 {

    private static Singleton_02 instance;

    private Singleton_02() {
    }
    public static synchronized Singleton_02 getInstance(){
        if (null != instance) return instance;
        instance = new Singleton_02();
        return instance;
    }
}
```
* 3.饿汉模式(线程安全)
```
public class Singleton_03 {

    private static Singleton_03 instance = new Singleton_03();

    private Singleton_03() {
    }
    public static Singleton_03 getInstance() {
        return instance;
    }
}
```
* 4.使用类的内部类(线程安全)
```
public class Singleton_04 {


    private static class SingletonHolder {
        private static Singleton_04 instance = new Singleton_04();
    }

    private Singleton_04() {
    }

    public static Singleton_04 getInstance() {
        return SingletonHolder.instance;
    }

}
```
* 5.双重锁校验(线程安全)
```
public class Singleton_05 {


    private static volatile Singleton_05 instance;

    private Singleton_05() {
    }

    public static Singleton_05 getInstance(){
        if(null != instance) return instance;
        synchronized (Singleton_05.class){
            if (null == instance){
                instance = new Singleton_05();
            }
        }
        return instance;
    }
}
```
* 6.CAS「AtomicReference」(线程安全)
```
public class Singleton_06 {


    private static final AtomicReference<Singleton_06> INSTANCE = new AtomicReference<Singleton_06>();

    private Singleton_06() {
    }

    public static final Singleton_06 getInstance() {
        for (; ; ) {
            Singleton_06 instance = INSTANCE.get();
            if (null != instance) return instance;
            INSTANCE.compareAndSet(null, new Singleton_06());
            return INSTANCE.get();
        }
    }
```
* 7.Effective Java作者推荐的枚举单例(线程安全)
```
public enum Singleton_07 {

    INSTANCE;
    public void test(){
        System.out.println("hi~");
    }
}

```
# 结构型模式
## 适配器模式
* 适配器模式的主要作用就是把原本不兼容的接口，通过适配修改做到统一
* 1.实体对象适配，service服务适配（实现同一个接口即可）
* 2.现有一个`create_account`对象需要转换为`RebateInfo`对象
* 3.字段转换适配器
```
public class MQAdapter {

    public static RebateInfo filter(String strJson, Map<String, String> link) throws Exception {
        return filter(JSON.parseObject(strJson, Map.class), link);
    }

    /**
     * 消息过滤转换
     *
     * @param obj  数据实体
     * @param link key对应关系(key:rebateInfo字段名  value:原先字段名)
     * @return
     */
    public static RebateInfo filter(Map obj, Map<String, String> link) throws Exception {
        RebateInfo rebateInfo = new RebateInfo();
        for (String key : link.keySet()) {
            Object val = obj.get(link.get(key));
            RebateInfo.class.getMethod("set" + key.substring(0, 1).toUpperCase() + key.substring(1),
                    String.class).invoke(rebateInfo, val);
        }
        return rebateInfo;
    }
}
```
* 4.测试使用
```
    @Test
    public void test() throws Exception {
        create_account create_account = new create_account();
        create_account.setNumber("100001");
        create_account.setAddress("河北省.廊坊市.广阳区.大学里职业技术学院");
        create_account.setAccountDate(new Date());
        create_account.setDesc("在校开户");

        HashMap<String, String> link01 = new HashMap<String, String>();
        link01.put("userId", "number");
        link01.put("bizId", "number");
        link01.put("desc", "desc");
        RebateInfo rebateInfo01 = MQAdapter.filter(JSON.toJSONString(create_account), link01);
        System.out.println("mq.create_account(适配前)" + create_account.toString());
        System.out.println("mq.create_account(适配后)" + JSON.toJSONString(rebateInfo01));

        System.out.println("======service适配（实现同一个接口即可）===========");
        OrderAdapterService popOrderAdapterService = new POPOrderAdapterServiceImpl();
        System.out.println("判断首单，接口适配(POP)：" + popOrderAdapterService.isFirst("100001"));

        OrderAdapterService insideOrderService = new InsideOrderServiceImpl();
        System.out.println("判断首单，接口适配(自营)：" + insideOrderService.isFirst("100001"));
    }
```
## 桥接模式
* 桥接模式的主要作用就是通过将抽象部分与实现部分分离，把多种可匹配的使用进行组合。说白了核心实现也就是在A类中含有B类接口，通过构造函数传递B类的实现，这个B类就是设计的桥
* 1.定义一个支付方式接口`IPayMode`,下面有n个实现类`PayCypher`、`PayFaceMode`、`PayFingerprintMode`
```
public interface IPayMode {

    /**
     * 支付是否安全
     * @param uId
     * @return
     */
    boolean security(String uId);

}
```
```
public class PayFaceMode implements IPayMode{
    protected Logger logger = LoggerFactory.getLogger(IPayMode.class);

    @Override
    public boolean security(String uId) {
        logger.info("人脸支付，风控校验脸部识别");
        return true;
    }
}
```
* 2.抽象一个支付类`Pay`，下面有两个支付实体`WxPay`、`ZfbPay`，抽象类的构造方法会将支付方式作为参数传递进来，`ZfbPay`实体就能根据传递进来的支付方式进行支付了
```
public abstract class Pay {

    protected Logger logger = LoggerFactory.getLogger(Pay.class);

    protected IPayMode payMode;

    Pay(IPayMode payMode){
        this.payMode = payMode;
    }

    public abstract String transfer(String uId, String tradeId, BigDecimal amount);

}
```
```
public class ZfbPay extends Pay{

    public ZfbPay(IPayMode payMode) {
        super(payMode);
    }

    @Override
    public String transfer(String uId, String tradeId, BigDecimal amount) {
        logger.info("模拟支付宝渠道支付划账开始。uId：{} tradeId：{} amount：{}", uId, tradeId, amount);
        boolean security = payMode.security(uId);
        logger.info("模拟支付宝渠道支付风控校验。uId：{} tradeId：{} security：{}", uId, tradeId, security);
        if (!security) {
            logger.info("模拟支付宝渠道支付划账拦截。uId：{} tradeId：{} amount：{}", uId, tradeId, amount);
            return "0001";
        }
        logger.info("模拟支付宝渠道支付划账成功。uId：{} tradeId：{} amount：{}", uId, tradeId, amount);
        return "0000";
    }
}
```
* 3.测试使用
```
public class ApiTest {

    @Test
    public void test(){

        System.out.println("\r\n模拟测试场景；微信支付、人脸方式。");
        Pay wxPay = new WxPay(new PayFaceMode());
        wxPay.transfer("weixin_1092033111", "100000109893", new BigDecimal(100));

        System.out.println("\r\n模拟测试场景；支付宝支付、指纹方式。");
        Pay zfbPay = new ZfbPay(new PayFingerprintMode());
        zfbPay.transfer("jlu19dlxo111", "100000109894", new BigDecimal(100));
    }
}
```
## 组合模式
* 通过把相似对象(也可以称作是方法)组合成一组可被调用的结构树对象的设计思路叫做组合模式，并且能够以统一的方式处理单个对象以及组合对象
* 1.一个相对简单的例子
```
// 组件接口
interface Component {
    void operation();
}

// 叶子类
class Leaf implements Component {
    public void operation() {
        System.out.println("Leaf operation");
    }
}

// 容器类
class Composite implements Component {
    private List<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        components.add(component);
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    public void operation() {
        System.out.println("Composite operation");
        // 遍历子组件并调用其操作方法
        for (Component component : components) {
            component.operation();
        }
    }
}

// 客户端代码
public class Client {
    public static void main(String[] args) {
        Component leaf1 = new Leaf();
        Component leaf2 = new Leaf();
        Component composite = new Composite();

        composite.addComponent(leaf1);
        composite.addComponent(leaf2);

        composite.operation();
    }
}
```
* 2.输出结果
```
Composite operation
Leaf operation
Leaf operation
```
## 装饰器模式
* 通过创建包装器对象来包裹原始对象，从而在不修改原始对象的情况下动态地扩展其功能。
* 1.原有一个拦截器接口`HandlerInterceptor`，以及一个实现类`SsoInterceptor`，在不改变原有类的情况下 我们对其做扩展
```
public interface HandlerInterceptor {

    boolean preHandle(String request, String response, Object handler);
}
```
```
public class SsoInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(String request, String response, Object handler) {
        // 模拟获取cookie
        String ticket = request.substring(1, 8);
        // 模拟校验
        return ticket.equals("success");
    }
}
```
* 2.抽象类装饰角色（将原有的逻辑接口接入）`SsoDecorator`，并针对抽象类进行继承增强`LoginSsoDecorator`
```
public abstract class SsoDecorator {

    private HandlerInterceptor handlerInterceptor;

    /**
     * 将原有的逻辑方法传入
     * @param handlerInterceptor
     */
    public SsoDecorator(HandlerInterceptor handlerInterceptor){
        this.handlerInterceptor = handlerInterceptor;
    }

    /**
     * 继承原有的逻辑方法
     * @param request
     * @param response
     * @param handler
     * @return
     */
    public boolean preHandle(String request, String response, Object handler) {
        return handlerInterceptor.preHandle(request, response, handler);
    }
}
```
```
public class LoginSsoDecorator extends SsoDecorator{

    private Logger logger = LoggerFactory.getLogger(LoginSsoDecorator.class);

    private static Map<String, String> authMap = new ConcurrentHashMap<String, String>();

    static {
        authMap.put("huahua", "queryUserInfo");
        authMap.put("doudou", "queryUserInfo");
    }

    /**
     * 将原有的逻辑方法传入
     *
     * @param handlerInterceptor
     */
    public LoginSsoDecorator(HandlerInterceptor handlerInterceptor) {
        super(handlerInterceptor);
    }

    @Override
    public boolean preHandle(String request, String response, Object handler) {
        // 先调用原有的逻辑方法
        boolean success = super.preHandle(request, response, handler);
        // 自己新增的逻辑
        if (!success) return false;
        String userId = request.substring(8);
        String method = authMap.get(userId);
        logger.info("模拟单点登录方法访问拦截校验：{} {}", userId, method);
        // 模拟方法校验
        return "queryUserInfo".equals(method);
    }
}
```
* 3.测试使用
```
public class ApiTest {

    @Test
    public void test_LoginSsoDecorator() {
        LoginSsoDecorator ssoDecorator = new LoginSsoDecorator(new SsoInterceptor());
        String request = "1successhuahua";
        boolean success = ssoDecorator.preHandle(request, "ewcdqwt40liuiu", "t");
        System.out.println("登录校验：" + request + (success ? " 放行" : " 拦截"));
    }
}
```

## 外观模式
* 将一组复杂的子系统封装起来，对外提供一个简单的接口，以简化客户端与子系统的交互
* 1.在实际开发中我们会有多个controller，每个controller都需要拦截处理 校验用户是否能够访问(白名单)，这个时候我们可以用自定义注解`DoDoor`  + aop来进行处理
```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoDoor {

    /**
     * 拦截字段值
     * @return
     */
    String key() default "";

    /**
     * 拦截时返回json
     * @return
     */
    String returnJson() default "";

}
```
* 2.aop实现自定义注解逻辑
```
@Aspect
@Component
public class DoJoinPoint {

    private Logger logger = LoggerFactory.getLogger(DoJoinPoint.class);

    @Value("${userIdStr}")
    public String userIdStr;

    @Pointcut("@annotation(com.imwj.design.door.annotation.DoDoor)")
    public void aopPoint(){
    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        // 获取方法内容
        Method method = getMethod(jp);
        // 获取注解中的字段值
        DoDoor door = method.getAnnotation(DoDoor.class);
        String keyValue = getFiledValue(door.key(), jp.getArgs());
        logger.info("door handler method：{} value：{}", method.getName(), keyValue);
        // 获取不到值直接放行
        if (null == keyValue || "".equals(keyValue)) return jp.proceed();
        // 白名单放行
        if(checkUserIdIntercept(keyValue))return jp.proceed();
        // 拦截并返回
        return returnObject(door, method);
    }

    /**
     * 获取方法
     * @param jp
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return getClass(jp).getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp) throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }

    /**
     * 校验用户id是否需要拦截
     * @param userId
     * @return
     */
    private Boolean checkUserIdIntercept(String userId){
        return userIdStr.contains(userId);
    }

    /**
     * 返回对象
     * @param doGate
     * @param method
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object returnObject(DoDoor doGate, Method method) throws IllegalAccessException, InstantiationException {
        Class<?> returnType = method.getReturnType();
        String returnJson = doGate.returnJson();
        if ("".equals(returnJson)) {
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    /**
     * 获取属性值
     * @param filed
     * @param args
     * @return
     */
    private String getFiledValue(String filed, Object[] args) {
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (null == filedValue || "".equals(filedValue)) {
                    filedValue = BeanUtils.getProperty(arg, filed);
                } else {
                    break;
                }
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }
        }
        return filedValue;
    }
}
```
* 3.controller层使用
```
@RestController
public class HelloController {

    @DoDoor(key = "userId", returnJson = "{\"code\":\"1111\",\"info\":\"非白名单用户拦截！\"}")
    @RequestMapping(path = "/api/queryUserInfo", method = RequestMethod.GET)
    public UserInfo queryUserInfo(@RequestParam String userId) {
        return new UserInfo("团团:" + userId, 19, "天津市南开区旮旯胡同100号");
    }
}

```
## 享元模式
* 享元模式，主要在于共享通用对象，减少内存的使用，提升系统的访问效率
* 1.有一个活动商品类`Activity`，里面有商品基础信息以及库存信息`Stock`，我们希望通过数据库存储商品信息 redis里存储商品库存
```
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    /** 活动ID */
    private Long id;
    /** 活动名称 */
    private String name;
    /** 活动描述 */
    private String desc;
    /** 开始时间 */
    private Date startTime;
    /** 结束时间 */
    private Date stopTime;
    /** 活动库存 */
    private Stock stock;
}
```
```
@Data
@AllArgsConstructor
public class Stock {

    /** 库存总量 */
    private int total;
    /** 库存已用 */
    private int used;
}
```
* 2.`RedisUtils`存储库存信息，获取时通过redis工具类获取
```
public class RedisUtils {


    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    private AtomicInteger stock = new AtomicInteger(0);

    public RedisUtils() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // 模拟库存消耗
            stock.addAndGet(1);
        }, 0, 100000, TimeUnit.MICROSECONDS);

    }

    public int getStockUsed() {
        return stock.get();
    }
}
``` 
* 3.controller层获取库存
```
public class ActivityController {

    private RedisUtils redisUtils = new RedisUtils();

    public Activity queryActivityInfo(Long id) {
        Activity activity = ActivityFactory.queryInfo(id);
        // 模拟从Redis中获取库存变化信息
        Stock stock = new Stock(1000, redisUtils.getStockUsed());
        activity.setStock(stock);
        return activity;
    }
}
```
## 代理模式
* 它允许通过代理对象控制对另一个对象的访问。代理模式创建了一个代理对象，代理对象与原始对象具有相同的接口，客户端通过代理对象间接地访问原始对象，从而可以在不改变原始对象的情况下增加额外的功能或控制访问
* 常见的如：mybatis中只需要定义一个接口 然后在注解或xml中配置sql即能执行sql查询
* 1.定义一个`IUserDao`接口，一个`@Select`注解
```
public interface IUserDao {

    @Select("select userName from user where id = #{uId}")
    String queryUserInfo(String uId);

}
```
```
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Select {

    /**
     * sql语句
     * @return
     */
    String value() default "";
}
```
* 2.代理类定义`MapperFactoryBean`
```
public class MapperFactoryBean<T> implements FactoryBean<T> {

    private Logger logger = LoggerFactory.getLogger(MapperFactoryBean.class);

    private Class<T> mapperInterface;

    public MapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * 创建代理对象
     * @return
     * @throws Exception
     */
    @Override
    public T getObject() throws Exception {
        InvocationHandler handler = (proxy, method, args) ->{
            Select select = method.getAnnotation(Select.class);
            logger.info("SQL：{}", select.value().replace("#{uId}", args[0].toString()));
            return args[0] + ",乐于分享！";
        };
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperInterface}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
```
* 3.注册beanFactory
```
public class RegisterBeanFactory implements BeanDefinitionRegistryPostProcessor {


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MapperFactoryBean.class);
        beanDefinition.setScope("singleton");
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(IUserDao.class);

        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(beanDefinition, "userDao");
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
```
* 4.`spring-config.xml`中配置扫描；路径
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"
       default-autowire="byName">

    <bean id="userDao" class="com.imwj.design.agent.RegisterBeanFactory"/>

</beans>
```
* 5.注入使用
```
    @Test
    public void test_IUserDao() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring-config.xml");
        IUserDao userDao = beanFactory.getBean("userDao", IUserDao.class);
        String res = userDao.queryUserInfo("100001");
        logger.info("测试结果：{}", res);
    }
```

# 行为型模式
## 责任链模式
* 用来处理相关事务责任的一条执行链，执行链上有多个节点，每个节点都有机会（条件匹配）处理请求事务，如果某个节点处理完了就可以根据实际业务需求传递给下一个节点继续处理或者返回处理完毕。
* Spring拦截器链、servlet过滤器链等都采用了责任链设计模式。
```
/**
 * 责任链抽象处理类
 * @author langao_q
 * @since 2021-12-29 15:37
 */
public class AbstractLeaveHandler {
    /**三级领导处理*/
    protected int MIN = 10;
    /**二级领导处理*/
    protected int MIDDLE = 20;
    /**一级级领导处理*/
    protected int MAX = 30;
    /**领导名称*/
    protected String handlerName;
    /**下一个处理节点（即更高级别的领导）*/
    protected AbstractLeaveHandler nextHandler;
    /**设置下一节点*/
    protected void setNextHandler(AbstractLeaveHandler handler){
        this.nextHandler = handler;
    }
    /**处理请求，子类实现*/
    protected void handlerRequest(LeaveRequest request){
    }
}
```
```
/**
 * 请求实体
 * @author langao_q
 * @since 2021-12-29 15:37
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {
    /**天数*/
    private int leaveDays;
    /**姓名*/
    private String name;
}
```
```
/**
 *  一级领导
 * @author langao_q
 * @since 2021-12-29 15:39
 */
public class OneLeaveHandler extends AbstractLeaveHandler {
    public OneLeaveHandler(String name) {
        this.handlerName = name;
    }
    @Override
    protected void handlerRequest(LeaveRequest request) {
        if(request.getLeaveDays() > this.MIDDLE && request.getLeaveDays() <= this.MAX){
            System.out.println(handlerName + ",已经处理;流程结束。");
            return;
        }
        if(null != this.nextHandler){
            this.nextHandler.handlerRequest(request);
        }else{
            System.out.println("审批拒绝！");
        }
    }
}
```
```
/**
 * 二级领导
 * @author langao_q
 * @since 2021-12-29 15:38
 */
public class TwoLeaveHandler extends AbstractLeaveHandler {
    public TwoLeaveHandler(String name) {
        this.handlerName = name;
    }
    @Override
    protected void handlerRequest(LeaveRequest request) {
        if(request.getLeaveDays() >this.MIN && request.getLeaveDays() <= this.MIDDLE){
            System.out.println(handlerName + ",已经处理;流程结束。");
            return;
        }
        if(null != this.nextHandler){
            this.nextHandler.handlerRequest(request);
        }else{
            System.out.println("审批拒绝！");
        }
    }
}
```

```
/**
 * 三级领导
 * @author langao_q
 * @since 2021-12-29 15:38
 */
public class ThreeLeaveHandler extends AbstractLeaveHandler{
    public ThreeLeaveHandler(String name) {
        this.handlerName = name;
    }
    @Override
    protected void handlerRequest(LeaveRequest request) {
        if(request.getLeaveDays() <= this.MIN){
            System.out.println(handlerName + ",已经处理;流程结束。");
            return;
        }
        if(null != this.nextHandler){
            this.nextHandler.handlerRequest(request);
        }else{
            System.out.println("审批拒绝！");
        }
    }
}
```
```
/**
 * 测试类
 * @author langao_q
 * @since 2021-12-29 15:39
 */
public class MainTest {
    public static void main(String[] args) {
        //根据leaveDays的值来决定是哪一级别的领导处理
        LeaveRequest request = LeaveRequest.builder().leaveDays(50).name("测试").build();
        /**
         * 三级(10) < 二级(20) < 一级(30)；三级领导能处理就不往上走了  三级处理不了再抛给二级领导
         */
        AbstractLeaveHandler directLeaderLeaveHandler = new ThreeLeaveHandler("三级领导");
        TwoLeaveHandler deptManagerLeaveHandler = new TwoLeaveHandler("二级领导");
        OneLeaveHandler gManagerLeaveHandler = new OneLeaveHandler("一级领导");
        //将各个处理类串联起来
        directLeaderLeaveHandler.setNextHandler(deptManagerLeaveHandler);
        deptManagerLeaveHandler.setNextHandler(gManagerLeaveHandler);
        //处理方法
        directLeaderLeaveHandler.handlerRequest(request);
    }
}
```
## 命令模式
* 将请求和操作进行解耦，以便能够将请求封装成独立的对象，并在需要时进行参数化
* 1.新建一个`ICook`厨师接口、一个`ICuisine`菜系接口（菜系实现类中将对应的厨师实现类作为构造参数传入），两个接口各自有各自的n个实现类
```
public class GuangDongCook implements ICook{

    private Logger logger = LoggerFactory.getLogger(ICook.class);

    @Override
    public void doCooking() {
        logger.info("广东厨师，烹饪粤菜，宫廷菜系，以孔府风味为龙头");
    }
}

public class GuangDoneCuisine implements ICuisine {

    private ICook cook;

    private GuangDoneCuisine() {
    }

    public GuangDoneCuisine(ICook cook) {
        this.cook = cook;
    }

    @Override
    public void cook() {
        cook.doCooking();
    }

}
```
* 2.新建一个`XiaoEr`小二类，将对应的菜系作为构造参数传入
```
public class XiaoEr {


    private Logger logger = LoggerFactory.getLogger(XiaoEr.class);

    private List<ICuisine> cuisineList = new ArrayList<ICuisine>();

    public void order(ICuisine cuisine) {
        cuisineList.add(cuisine);
    }

    public synchronized void placeOrder() {
        for (ICuisine cuisine : cuisineList) {
            cuisine.cook();
        }
        cuisineList.clear();
    }
}
```
* 3.测试使用：构建厨师、菜系将菜系放入小二类的订单中
```
public class ApiTest {

    @Test
    public void test_xiaoEr(){
        // 菜系 + 厨师；广东（粤菜）、江苏（苏菜）、山东（鲁菜）、四川（川菜）
        ICuisine guangDoneCuisine = new GuangDoneCuisine(new GuangDongCook());
        JiangSuCuisine jiangSuCuisine = new JiangSuCuisine(new JiangSuCook());

        // 点单
        XiaoEr xiaoEr = new XiaoEr();
        xiaoEr.order(guangDoneCuisine);
        xiaoEr.order(jiangSuCuisine);

        xiaoEr.placeOrder();

    }
}
```

## 迭代器模式
* 略.
## 中介者模式
* 解决的就是复杂功能应用之间的重复调用，在这中间添加一层中介者包装服务，对外提供简单、通用、易扩展的服务能力。
* 1.新建一个`SqlSession`接口，以及一个对应实现类`DefaultSqlSession`
```
public class DefaultSqlSession implements SqlSession{

    private Connection connection;
    /**
     * key：dao路径 + 方法名，value：xml中的sql信息
     */
    private Map<String, XNode> mapperElement;

    public DefaultSqlSession(Connection connection, Map<String, XNode> mapperElement) {
        this.connection = connection;
        this.mapperElement = mapperElement;
    }

    @Override
    public <T> T selectOne(String statement) {
        try {
            XNode xNode = mapperElement.get(statement);
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> objects = resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
            return objects.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        XNode xNode = mapperElement.get(statement);
        Map<Integer, String> parameterMap = xNode.getParameter();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            buildParameter(preparedStatement, parameter, parameterMap);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> objects = resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
            return objects.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statement) {
        try {
            XNode xNode = mapperElement.get(statement);
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> objects = resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statement, Object parameter) {
        XNode xNode = mapperElement.get(statement);
        Map<Integer, String> parameterMap = xNode.getParameter();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            buildParameter(preparedStatement, parameter, parameterMap);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> objects = resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        if (null == connection) return;
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * jdbc查询结果转换为指定对象
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

    private void buildParameter(PreparedStatement preparedStatement, Object parameter, Map<Integer, String> parameterMap) throws SQLException, IllegalAccessException {

        int size = parameterMap.size();
        // 单个参数
        if (parameter instanceof Long) {
            for (int i = 1; i <= size; i++) {
                preparedStatement.setLong(i, Long.parseLong(parameter.toString()));
            }
            return;
        }

        if (parameter instanceof Integer) {
            for (int i = 1; i <= size; i++) {
                preparedStatement.setInt(i, Integer.parseInt(parameter.toString()));
            }
            return;
        }

        if (parameter instanceof String) {
            for (int i = 1; i <= size; i++) {
                preparedStatement.setString(i, parameter.toString());
            }
            return;
        }

        Map<String, Object> fieldMap = new HashMap<>();
        // 对象参数
        Field[] declaredFields = parameter.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            String name = field.getName();
            field.setAccessible(true);
            Object obj = field.get(parameter);
            field.setAccessible(false);
            fieldMap.put(name, obj);
        }

        for (int i = 1; i <= size; i++) {
            String parameterDefine = parameterMap.get(i);
            Object obj = fieldMap.get(parameterDefine);

            if (obj instanceof Short) {
                preparedStatement.setShort(i, Short.parseShort(obj.toString()));
                continue;
            }

            if (obj instanceof Integer) {
                preparedStatement.setInt(i, Integer.parseInt(obj.toString()));
                continue;
            }

            if (obj instanceof Long) {
                preparedStatement.setLong(i, Long.parseLong(obj.toString()));
                continue;
            }

            if (obj instanceof String) {
                preparedStatement.setString(i, obj.toString());
                continue;
            }

            if (obj instanceof Date) {
                preparedStatement.setDate(i, (java.sql.Date) obj);
            }

        }

    }
}
```
* 2.新建一个`SqlSessionFactory`接口，以及对应实现类`DefaultSqlSessionFactory`，其作用主要是配置和获取`DefaultSqlSession`
```
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration.connection, configuration.mapperElement);
    }
}
```
* 3.新建一个`SqlSessionFactoryBuilder`，主要用于读取`User_Mapper.xml`构建`DefaultSqlSessionFactory`
```
public class SqlSessionFactoryBuilder {

    public DefaultSqlSessionFactory build(Reader reader) {
        SAXReader saxReader = new SAXReader();
        try {
            saxReader.setEntityResolver(new XMLMapperEntityResolver());
            Document document = saxReader.read(new InputSource(reader));
            Configuration configuration = parseConfiguration(document.getRootElement());
            return new DefaultSqlSessionFactory(configuration);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Configuration parseConfiguration(Element root) {
        Configuration configuration = new Configuration();
        configuration.setDataSource(dataSource(root.selectNodes("//dataSource")));
        configuration.setConnection(connection(configuration.dataSource));
        configuration.setMapperElement(mapperElement(root.selectNodes("mappers")));
        return configuration;
    }

    // 获取数据源配置信息
    private Map<String, String> dataSource(List<Element> list) {
        Map<String, String> dataSource = new HashMap<>(4);
        Element element = list.get(0);
        List content = element.content();
        for (Object o : content) {
            Element e = (Element) o;
            String name = e.attributeValue("name");
            String value = e.attributeValue("value");
            dataSource.put(name, value);
        }
        return dataSource;
    }

    private Connection connection(Map<String, String> dataSource) {
        try {
            Class.forName(dataSource.get("driver"));
            return DriverManager.getConnection(dataSource.get("url"), dataSource.get("username"), dataSource.get("password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取SQL语句信息
    private Map<String, XNode> mapperElement(List<Element> list) {
        Map<String, XNode> map = new HashMap<>();

        Element element = list.get(0);
        List content = element.content();
        for (Object o : content) {
            Element e = (Element) o;
            String resource = e.attributeValue("resource");

            try {
                Reader reader = Resources.getResourceAsReader(resource);
                SAXReader saxReader = new SAXReader();
                Document document = saxReader.read(new InputSource(reader));
                Element root = document.getRootElement();
                //命名空间
                String namespace = root.attributeValue("namespace");

                // SELECT
                List<Element> selectNodes = root.selectNodes("select");
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

                    XNode xNode = new XNode();
                    xNode.setNamespace(namespace);
                    xNode.setId(id);
                    xNode.setParameterType(parameterType);
                    xNode.setResultType(resultType);
                    xNode.setSql(sql);
                    xNode.setParameter(parameter);

                    map.put(namespace + "." + id, xNode);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return map;
    }

}
```
* 4.测试使用，读取`mybatis-config-datasource.xml`配置 然后构建`SqlSessionFactory`对象，最后获取到`SqlSession`对象用于执行sql
```
    @Test
    public void test_queryUserInfoById() {
        String resource = "mybatis-config-datasource.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlMapper.openSession();
            try {
                User user = session.selectOne("com.imwj.design.dao.IUserDao.queryUserInfoById", "62baae12-41cb-11eb-8d21-00163e0cd193");
                logger.info("测试结果：{}", JSON.toJSONString(user));
            } finally {
                session.close();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
## 备忘录模式
* 以回滚，配置、版本、悔棋为核心功能的设计模式，在功能实现上是以不破坏原对象为基础增加备忘录操作类，记录原对象的行为从而实现备忘录模式。
* 1.新建一个配置文件类`ConfigFile`，主要用于记录版本的相关信息
```
@Data
@AllArgsConstructor
public class ConfigFile {

    private String versionNo; // 版本号
    private String content;   // 内容
    private Date dateTime;    // 时间
    private String operator;  // 操作人
}
```
* 2.新建一个备忘录类`ConfigMemento`，对原有配置类的扩展，可以设置和获取配置信息
```
public class ConfigMemento {

    private ConfigFile configFile;

    public ConfigMemento(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(ConfigFile configFile) {
        this.configFile = configFile;
    }
}
```
* 3.新建一个记录者类`ConfigOriginator`，保存操作的相关信息
```
public class ConfigOriginator {

    private ConfigFile configFile;

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public ConfigMemento saveMemento(){
        return new ConfigMemento(configFile);
    }

    public void getMemento(ConfigMemento memento){
        this.configFile = memento.getConfigFile();
    }
}
```
* 4.创建一个管理员类`Admin`，主要操作回滚、前进、跳转到指定版本
```
public class Admin {

    private int cursorIdx = 0;
    private List<ConfigMemento> mementoList = new ArrayList<>();
    private Map<String, ConfigMemento> mementoMap = new ConcurrentHashMap<>();

    public void append(ConfigMemento memento){
        mementoList.add(memento);
        mementoMap.put(memento.getConfigFile().getVersionNo(), memento);
        cursorIdx ++;
    }

    public ConfigMemento undo(){
        if(--cursorIdx <= 0){
            return mementoList.get(0);
        }
        return mementoList.get(cursorIdx);
    }

    public ConfigMemento  redo(){
        if(++cursorIdx > mementoList.size()){
            return mementoList.get(mementoList.size() - 1);
        }
        return mementoList.get(cursorIdx);
    }

    public ConfigMemento get(String versionNo){
        return mementoMap.get(versionNo);
    }

}
```
* 5.测试使用
```
    @Test
    public void test() {
        Admin admin = new Admin();
        ConfigOriginator configOriginator = new ConfigOriginator();

        configOriginator.setConfigFile(new ConfigFile("1000001", "配置内容A=哈哈", new Date(), "imwj"));
        admin.append(configOriginator.saveMemento()); // 保存配置

        configOriginator.setConfigFile(new ConfigFile("1000002", "配置内容A=嘻嘻", new Date(), "imwj"));
        admin.append(configOriginator.saveMemento()); // 保存配置

        configOriginator.setConfigFile(new ConfigFile("1000003", "配置内容A=么么", new Date(), "imwj"));
        admin.append(configOriginator.saveMemento()); // 保存配置

        configOriginator.setConfigFile(new ConfigFile("1000004", "配置内容A=嘿嘿", new Date(), "imwj"));
        admin.append(configOriginator.saveMemento()); // 保存配置  

        // 历史配置(回滚)
        configOriginator.getMemento(admin.undo());
        logger.info("历史配置(回滚)undo：{}", JSON.toJSONString(configOriginator.getConfigFile()));

        // 历史配置(回滚)
        configOriginator.getMemento(admin.undo());
        logger.info("历史配置(回滚)undo：{}", JSON.toJSONString(configOriginator.getConfigFile()));

        // 历史配置(前进)
        configOriginator.getMemento(admin.redo());
        logger.info("历史配置(前进)redo：{}", JSON.toJSONString(configOriginator.getConfigFile()));

        // 历史配置(获取)
        configOriginator.getMemento(admin.get("1000002"));
        logger.info("历史配置(获取)get：{}", JSON.toJSONString(configOriginator.getConfigFile()));
    }
```

## 观察者模式
* 当一个行为发生时传递信息给另外一个用户接收做出相应的处理，两者之间没有直接的耦合关联。
* 1.新建一个监听接口`EventListener`,对应的有两个实现类`MessageEventListener`和`MQEventListener`
```
public class MessageEventListener implements EventListener{
    private Logger logger = LoggerFactory.getLogger(MessageEventListener.class);

    @Override
    public void doEvent(LotteryResult result) {
        logger.info("给用户 {} 发送短信通知(短信)：{}", result.getUId(), result.getMsg());
    }
}
```
* 2.新建一个监听事件处理类`EventManager`
```
public class EventManager {

    Map<Enum<EventType>, List<EventListener>> listeners = new HashMap<>();

    public enum EventType {
        MQ, Message
    }

    public EventManager(Enum<EventType>... operations) {
        for (Enum<EventType> operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }


    /**
     * 订阅
     * @param eventType
     * @param listener
     */
    public void subscribe(Enum<EventType> eventType, EventListener listener){
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    /**
     * 取消订阅
     * @param eventType
     * @param listener
     */
    public void unsubscribe(Enum<EventType> eventType, EventListener listener){
        List<EventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    /**
     * 消息通知
     * @param eventType
     * @param result
     */
    public void notify(Enum<EventType> eventType, LotteryResult result){
        List<EventListener> users  = listeners.get(eventType);
        for(EventListener listener : users){
            listener.doEvent(result);
        }
    }
}
```
* 3.抽取业务类接口`LotteryService`(抽象类)
```
public abstract class LotteryService {

    private EventManager eventManager;

    public LotteryService(){
        eventManager = new EventManager(EventManager.EventType.MQ, EventManager.EventType.Message);
        eventManager.subscribe(EventManager.EventType.MQ, new MQEventListener());
        eventManager.subscribe(EventManager.EventType.Message, new MessageEventListener());
    }

    public LotteryResult draw(String uId){
        LotteryResult lotteryResult = doDraw(uId);
        // 通知方法
        eventManager.notify(EventManager.EventType.MQ, lotteryResult);
        eventManager.notify(EventManager.EventType.Message, lotteryResult);
        return lotteryResult;
    }

    /**
     * 真正的业务方法
     * @param uId
     * @return
     */
    protected abstract LotteryResult doDraw(String uId);

}
```
* 4.测试使用
```
    @Test
    public void test() {
        LotteryService lotteryService = new LotteryServiceImpl();
        LotteryResult result = lotteryService.draw("2765789109876");
        logger.info("测试结果：{}", JSON.toJSONString(result));
    }
```
## 状态模式
* 描述的是一个行为下的多种状态变更，比如我们最常见的一个网站的页面，在你登录与不登录下展示的内容是略有差异的(不登录不能展示个人信息)，而这种登录与不登录就是我们通过改变状态，而让整个行为发生了变化
* 1.新建一个状态抽象类`State`，定义各个状态变更的方法
```
public abstract class State {

    /**
     * 活动提审
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 执行结果
     */
    public abstract Result arraignment(String activityId, Enum<Status> currentStatus);

    /**
     * 审核通过
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 执行结果
     */
    public abstract Result checkPass(String activityId, Enum<Status> currentStatus);

    /**
     * 审核拒绝
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 执行结果
     */
    public abstract Result checkRefuse(String activityId, Enum<Status> currentStatus);

    /**
     * 撤审撤销
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 执行结果
     */
    public abstract Result checkRevoke(String activityId, Enum<Status> currentStatus);

    /**
     * 活动关闭
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 执行结果
     */
    public abstract Result close(String activityId, Enum<Status> currentStatus);

    /**
     * 活动开启
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 执行结果
     */
    public abstract Result open(String activityId, Enum<Status> currentStatus);

    /**
     * 活动执行
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 执行结果
     */
    public abstract Result doing(String activityId, Enum<Status> currentStatus);

}
```
* 2.新建两个状态实现类：编辑状态`EditingState`，提审状态`CheckState`，同时在各自对应的方法中实现相应逻辑
```
public class EditingState extends State {

    public Result arraignment(String activityId, Enum<Status> currentStatus) {
        ActivityService.execStatus(activityId, currentStatus, Status.Check);
        return new Result("0000", "活动提审成功");
    }

    public Result checkPass(String activityId, Enum<Status> currentStatus) {
        return new Result("0001", "编辑中不可审核通过");
    }

    public Result checkRefuse(String activityId, Enum<Status> currentStatus) {
        return new Result("0001", "编辑中不可审核拒绝");
    }

    @Override
    public Result checkRevoke(String activityId, Enum<Status> currentStatus) {
        return new Result("0001", "编辑中不可撤销审核");
    }

    public Result close(String activityId, Enum<Status> currentStatus) {
        ActivityService.execStatus(activityId, currentStatus, Status.Close);
        return new Result("0000", "活动关闭成功");
    }

    public Result open(String activityId, Enum<Status> currentStatus) {
        return new Result("0001", "非关闭活动不可开启");
    }

    public Result doing(String activityId, Enum<Status> currentStatus) {
        return new Result("0001", "编辑中活动不可执行活动中变更");
    }
}
```
```
public class CheckState extends State {

    public Result arraignment(String activityId, Enum<Status> currentStatus) {
        return new Result("0001", "待审核状态不可重复提审");
    }

    public Result checkPass(String activityId, Enum<Status> currentStatus) {
        ActivityService.execStatus(activityId, currentStatus, Status.Pass);
        return new Result("0000", "活动审核通过完成");
    }

    public Result checkRefuse(String activityId, Enum<Status> currentStatus) {
        ActivityService.execStatus(activityId, currentStatus, Status.Refuse);
        return new Result("0000", "活动审核拒绝完成");
    }

    @Override
    public Result checkRevoke(String activityId, Enum<Status> currentStatus) {
        ActivityService.execStatus(activityId, currentStatus, Status.Editing);
        return new Result("0000", "活动审核撤销回到编辑中");
    }

    public Result close(String activityId, Enum<Status> currentStatus) {
        ActivityService.execStatus(activityId, currentStatus, Status.Close);
        return new Result("0000", "活动审核关闭完成");
    }

    public Result open(String activityId, Enum<Status> currentStatus) {
        return new Result("0001", "非关闭活动不可开启");
    }

    public Result doing(String activityId, Enum<Status> currentStatus) {
        return new Result("0001", "待审核活动不可执行活动中变更");
    }
}
```
* 3.新建一个状态控制类`StateHandler`，用来控制状态变更
```
public class StateHandler {

    private Map<Enum<Status>, State> stateMap = new ConcurrentHashMap<Enum<Status>, State>();

    public StateHandler() {
        stateMap.put(Status.Check, new CheckState());     // 待审核
        // stateMap.put(Status.Close, new CloseState());     // 已关闭
        // stateMap.put(Status.Doing, new DoingState());     // 活动中
        stateMap.put(Status.Editing, new EditingState()); // 编辑中
        // stateMap.put(Status.Open, new OpenState());       // 已开启
        // stateMap.put(Status.Pass, new PassState());       // 审核通过
        // stateMap.put(Status.Refuse, new RefuseState());   // 审核拒绝
    }

    public Result arraignment(String activityId, Enum<Status> currentStatus) {
        return stateMap.get(currentStatus).arraignment(activityId, currentStatus);
    }

    public Result checkPass(String activityId, Enum<Status> currentStatus) {
        return stateMap.get(currentStatus).checkPass(activityId, currentStatus);
    }

    public Result checkRefuse(String activityId, Enum<Status> currentStatus) {
        return stateMap.get(currentStatus).checkRefuse(activityId, currentStatus);
    }

    public Result checkRevoke(String activityId, Enum<Status> currentStatus) {
        return stateMap.get(currentStatus).checkRevoke(activityId, currentStatus);
    }

    public Result close(String activityId, Enum<Status> currentStatus) {
        return stateMap.get(currentStatus).close(activityId, currentStatus);
    }

    public Result open(String activityId, Enum<Status> currentStatus) {
        return stateMap.get(currentStatus).open(activityId, currentStatus);
    }

    public Result doing(String activityId, Enum<Status> currentStatus) {
        return stateMap.get(currentStatus).doing(activityId, currentStatus);
    }

}
```
* 4.测试使用
```
    @Test
    public void test_Editing2Arraignment() {
        String activityId = "100001";
        // 初始化状态
        ActivityService.init(activityId, Status.Editing);
        StateHandler stateHandler = new StateHandler();
        // 获取对应状态操作类 并执行其中的方法
        Result result = stateHandler.arraignment(activityId, Status.Editing);
        logger.info("测试结果(编辑中To提审活动)：{}", JSON.toJSONString(result));
        logger.info("活动信息：{} 状态：{}", JSON.toJSONString(ActivityService.queryActivityInfo(activityId)), JSON.toJSONString(ActivityService.queryActivityInfo(activityId).getStatus()));
    }
```

## 策略模式(重点)
* 允许在运行时选择算法的行为。它定义了一系列算法，并将每个算法封装在独立的类中，使它们可以互相替换。通过使用策略模式，可以使算法的变化独立于使用算法的客户端。 策略模式的主要目的是将算法的定义与使用分离，
* 1.新建一个优惠券接口`ICouponDiscount`，该接口定义了一个优惠方法
```
public interface ICouponDiscount<T> {

    /**
     * 优惠券金额计算
     * @param couponInfo 券折扣信息；直减、满减、折扣、N元购
     * @param skuPrice   sku金额
     * @return           优惠后金额
     */
    BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice);
}
```
* 2.优惠券接口有n个对应实现类：满减`MJCouponDiscount`，直减`ZJCouponDiscount`，每个实现类有自己的计算方式 此处还用到了泛型
```
public class MJCouponDiscount implements ICouponDiscount<Map<String,String>> {

    /**
     * 满减计算
     * 1. 判断满足x元后-n元，否则不减
     * 2. 最低支付金额1元
     */
    @Override
    public BigDecimal discountAmount(Map<String,String> couponInfo, BigDecimal skuPrice) {
        String x = couponInfo.get("x");
        String o = couponInfo.get("n");

        // 小于商品金额条件的，直接返回商品原价
        if (skuPrice.compareTo(new BigDecimal(x)) < 0) return skuPrice;
        // 减去优惠金额判断
        BigDecimal discountAmount = skuPrice.subtract(new BigDecimal(o));
        if (discountAmount.compareTo(BigDecimal.ZERO) < 1) return BigDecimal.ONE;

        return discountAmount;
    }
}
```
```
public class ZJCouponDiscount implements ICouponDiscount<Double > {

    /**
     * 直减计算
     * 1. 使用商品价格减去优惠价格
     * 2. 最低支付金额1元
     */
    @Override
    public BigDecimal discountAmount(Double  couponInfo, BigDecimal skuPrice) {
        BigDecimal discountAmount = skuPrice.subtract(new BigDecimal(couponInfo));
        if (discountAmount.compareTo(BigDecimal.ZERO) < 1) return BigDecimal.ONE;
        return discountAmount;
    }
}
```
* 3.新建一个控制类，用于控制控制使用不同优惠方式`Context`
```
public class Context<T>{

    private ICouponDiscount<T> couponDiscount;

    public Context(ICouponDiscount<T> couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice) {
        return couponDiscount.discountAmount(couponInfo, skuPrice);
    }

}
```
* 4.测试使用
```
    @Test
    public void test_zj() {
        Context<Double> context = new Context<Double>(new ZJCouponDiscount());
        BigDecimal discountAmount = context.discountAmount(10D, new BigDecimal(100));
        logger.info("测试结果：直减优惠后金额 {}", discountAmount);

        Context<Map<String, String>> context1 = new Context<Map<String, String>>(new MJCouponDiscount());
        HashMap<String, String> map = new HashMap<>();
        map.put("x", "100");
        map.put("n", "30");
        BigDecimal discountAmount1 = context1.discountAmount(map, new BigDecimal(100));
        logger.info("测试结果：满减优惠后金额 {}", discountAmount1);
    }
```

## 模板方法模式(重点)
## 访问者模式
## 解释器模式。

> 最后大哥图片镇楼
![cmd-markdown-logo](http://blog.imwj.club//upload/2019/08/rtn7u1ns4uifdrcp6lipgtqhts.jpg)