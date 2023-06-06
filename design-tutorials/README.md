> 本文主要是对Java中一些常用的设计模式进行讲解 后期会进行不断的更新，欢迎浏览

# 23种设计模式
* 创建型模式，共五种：工厂方法模式、抽象工厂模式、单例模式、建造者模式、原型模式。
* 结构型模式，共七种：适配器模式、装饰器模式、代理模式、外观模式、桥接模式、组合模式、享元模式。
* 行为型模式，共十一种：策略模式、模板方法模式、观察者模式、迭代子模式、责任链模式、命令模式、备忘录模式、状态模式、访问者模式、中介者模式、解释器模式。

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
## 工厂方法模式
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
* 
## 组合模式
## 装饰器模式
## 外观模式
## 享元模式
## 代理模式


# 责任链模式
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

> 最后大哥图片镇楼
![cmd-markdown-logo](http://blog.imwj.club//upload/2019/08/rtn7u1ns4uifdrcp6lipgtqhts.jpg)