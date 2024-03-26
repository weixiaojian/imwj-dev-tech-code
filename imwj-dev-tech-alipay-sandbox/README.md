# [支付宝沙箱」支付接入流程
> 代码：https://github.com/weixiaojian/study-code/tree/master/imwj-dev-tech-alipay-sandbox 


![image](https://raw.githubusercontent.com/weixiaojian/study-code/master/imwj-dev-tech-alipay-sandbox/docs/img/Alipay.png)


## 1.准备工作：在[支付宝开放平台](https://open.alipay.com/develop/manage)中登录账号，并生成好对应的公钥和私钥

## 2.一个简单的单元测试案例，调用接口后会返回支付宝页面form代码
```
@Slf4j
@SpringBootTest
class ImwjDevTechAppApplicationTests {

	// 「沙箱环境」应用ID - 您的APPID，收款账号既是你的APPID对应支付宝账号。获取地址；https://open.alipay.com/develop/sandbox/app
	public static String app_id = "90210002131134629874";
	// 「沙箱环境」商户私钥，你的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG2139w0BAQEFA1231ASCBKcwggSjAgEAAoIBAQC6asle5Xw8aCYCIxEGGag9zc25wjeAERMxmcc1kfAMVU2G+fbyejIIEICGdpGiuLZ05GDPdes9uoXVGDWm1UAvfIoSZImQoqzntuMPYIsOMds7c3Yxi1OFSAIYPgwbUW1aHYeEDkO780Y0FJJ983SkKhS45LhSC/nx7lRG+Pns6RWFMEAFNVjeBX0WLouQNGDtJIIQgLwnhKp/sG05xX9PzA8PZGDFDZ+xPvpAe44XMXUUuDDUzQCJqvNahtkOSb5P4/F3mdDjmfx4h/KZBvrJF7PxQxsYcrqtGJu6bVAqEvFoTGnaYGe+2/u18qMcl3kJ9myjuCgqGvyGAKzltzBFAgMBAAECggEAeHoZSIZBo3yfy9PD5dBhJghDA+GcIt1mLTE4SMvXevEKWMhlq9IDUajwl3Y7qxF7gdIzKwEHlm5cnhq2WesKP2ZaiYaGTX6FBn/xAPghBnx8NlDpux1TThj6mZZIFsOqS4o4EcVySx7vaSs+fb4AzTQRDmlrIkNxNsUTnIa+Zzgl3OB+LHY5ONlqXXyvq8HHHfuSO+eelvk/G7Ph3NxkN7ig8j9n1Z6NGBXhN1TTchF62VnjtYD7+YUi+5HzUVu2BlVAiNS6NHsUK1OdX9+l6vfqMDXzG3NP9Fb2bQcTUe175q6rG+jNj1R3XWYU2WgTRtbu7KiCTKEDxuzE8ClBZQKBgQDhC1zWGd9X4b9NmDD1PnJlGTOPo3NuDnl5LzsYy04TprgWgZ2vANQ8smHdir8YZ1a8q4NE5A4WlOO+Iq0WHnQh+6RxwIPln18lFuJsxLbuaSeCaEyuuQzRJC10p/Obwj8QumpdmIymx+r+NsOynMcFd50rMXHR2l6/Ejoeh4Ek3wKBgQDUDzeKdIkoS9E/f2YHdpN04zwazEvs7zzBEZTBh6zTgRvllPwU9oPlCaP+O+j20iMixVn0K5AwkSI0oKsvmMMgFdQTtjUnq6ZL3i56nturYvemqfHXBnrlJJFqoj0RVA1oK4HZtmOerm+CGzXdUwP2LJgXkaW2z8ErbEP2A3aLWwKBgB1SWK9Awbv2mvFmQgLCNy97AB/YASM22Zbzk3SgkLS4xUWRb9Use3rJFz1BZbKtTDza8Aia2kcQ8TeF1x2JrEH9Qqjbl5PklwYDHE9lthwMMWypYExnqgbZ+t43P+NshMEOOOunA7R7Wd/zjL4J9Vh+BElrcNQR6sRkgJQXvbjrAoGBAMIWl2Aa3zpJgmAaq3ijN70hCe8BE3DtWm7fN4aWNkFzBuRvIvXQ1tE3rKhRjxzxIgV+GaHYiWVaQLNoZqXurJDeSbgs6aPAgDkqRuImKapIr6AUJWwH202E1a+ChGAqzQaN45AvVihzQU4Ut401oQecsyvHO6QBrKygr+coLkTHAoGAVfvMsgc/i6IHSerW4HjyxDHT0k9WpQJFCm5J79BYacRZhfGWmL2OGYx8uCawiV//f269SUmtAw1lKrz+eB9rTgtStmnf6rvfstNmflXn8iKgpqooR3JL8hfrdyOemyVLjlZAmngEjIIcswkhZmEPbAe7NR13NFXI0Ztacq5TxJE=";
	// 「沙箱环境」支付宝公钥
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOC123123AQ8AMIIBCgKCAQEAnqAVxBWdpXeWheEzILKdVs+o/Bq5Lcmjy6MhMHLqBjGlIjmjks/P+EV4Q1eg8/0a1KdRCRxhWDkifuq/S3fn98mZYm/I63g/W30bAAEicboPbl6jdiQj7wIBZPT6lgSt9rHzgXRguOURDA46sOYqpZH2U05xAMkYKGKsTu7zkGt/RkgeXMJkDlG7qAc9evt2zE6cPX5KsVbxwhEKfwdfBUAE2gIo+IAlkFh6py/vSxsBXR4ZbR1oSErKip2RmMROE98+iXTpNLEZkQ04MuFhTCzQj1tsXTUe0EDovD+5WFb3LpG7Qe9hhzu2I2bvGnVUr6CVPIFukpOYUnPqc0PU3QIDAQAB";
	// 「沙箱环境」服务器异步通知页面路径。这里用了 natapp.cn 内网穿透工具
	public static String notify_url = "http://6vkhpf.natappfree.cc/api/v1/alipay/alipay_notify_url";
	// 「沙箱环境」页面跳转同步通知页面路径 需http://格式的完整路径，必须外网可以正常访问，才会同步跳转
	public static String return_url = "http://blog.imwj.club/";
	// 「沙箱环境」
	public static String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
	// 签名方式
	public static String sign_type = "RSA2";
	// 字符编码格式
	public static String charset = "utf-8";

	@Test
	public void test_AliPay() throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
				app_id,
				merchant_private_key,
				"json",
				charset,
				alipay_public_key,
				sign_type);

		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
		request.setNotifyUrl(notify_url);
		request.setReturnUrl(return_url);

		JSONObject bizContent = new JSONObject();
		bizContent.put("out_trade_no", "d2323233333332332313361X02");  // 我们自己生成的订单编号
		bizContent.put("total_amount", "1000000"); // 订单的总金额
		bizContent.put("subject", "给莹莹转账1000000");   // 支付的名称
		bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
		request.setBizContent(bizContent.toString());

		String form = alipayClient.pageExecute(request).getBody();
		log.info("测试结果：{}", form);
	}
}
```

## DDD工程实现支付流程
* 1.业务分层
  * imwj-dev-tech-app：应用启动层，config和resource相关配置、test单元测试等
  * imwj-dev-tech-domain：领域层(核心业务逻辑实现)，包含（model实体、reoisitory调用基础层的数据持久化、service业务层）
  * imwj-dev-tech-infrastucture：基础设施层，如连接数据库的持久化
  * imwj-dev-tech-trigger：触发器层，如统一管理 http、mq、listener、job 等入口调用
  * imwj-dev-tech-types：业务类型存放，枚举、返回对象等

* 2.AliPayConfig读取yml配置并加载到spring中
```
@Configuration
@EnableConfigurationProperties(AliPayConfigProperties.class)
public class AliPayConfig
{

    @Bean(name = "alipayClient")
    @ConditionalOnProperty(value = "alipay.enabled", havingValue = "true", matchIfMissing = false)
    public AlipayClient alipayClient(AliPayConfigProperties properties){
        return new DefaultAlipayClient(properties.getGatewayUrl(),
                properties.getApp_id(),
                properties.getMerchant_private_key(),
                properties.getFormat(),
                properties.getCharset(),
                properties.getAlipay_public_key(),
                properties.getSign_type());
    }
}
```
* 3.`AliPayController`接收`index.html`的支付请求，同时接收回调
```
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/alipay/")
public class AliPayController {

    @Resource
    private IOrderService orderService;

    /**
     * http://localhost:8091/api/v1/alipay/create_pay_order?userId=1001&productId=100001
     *
     * @param userId
     * @param productId
     * @return
     */
    @RequestMapping(value = "create_pay_order", method = RequestMethod.POST)
    public Response<String> createParOrder(@RequestParam String userId, @RequestParam String productId) {
        try {
            log.info("商品下单，根据商品ID创建支付单开始 userId:{} productId:{}", userId, productId);
            ShopCartEntity shopCartEntity = ShopCartEntity.builder().userId(userId).productId(productId).build();
            PayOrderEntity payOrderEntity = orderService.createOrder(shopCartEntity);
            log.info("商品下单，根据商品ID创建支付单完成 userId:{} productId:{} orderId:{}", userId, productId, payOrderEntity.getOrderId());
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(payOrderEntity.getPayUrl())
                    .build();
        } catch (Exception e) {
            log.error("商品下单，根据商品ID创建支付单失败 userId:{} productId:{}", userId, productId, e);
            return Response.<String>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @RequestMapping(value = "pay_notify", method = RequestMethod.POST)
    public String payNotify(HttpServletRequest request) {
        log.info("支付回调，消息接收 {}", request.getParameter("trade_status"));
    }
}
```
* 4.`AbstractOrderService`+`OrderService`处理业务逻辑
```
public abstract class AbstractOrderService implements IOrderService {

    @Resource
    protected IOrderRepository repository;

    @Override
    public PayOrderEntity createOrder(ShopCartEntity shopCartEntity) throws Exception {
        // 1. 查询当前用户是否存在掉单和未支付订单
        OrderEntity unpaidOrderEntity = repository.queryUnPayOrder(shopCartEntity);
        if (null != unpaidOrderEntity && OrderStatusVO.PAY_WAIT.equals(unpaidOrderEntity.getOrderStatus())) {
            log.info("创建订单-存在，已存在未支付订单。userId:{} productId:{} orderId:{}", shopCartEntity.getUserId(), shopCartEntity.getProductId(), unpaidOrderEntity.getOrderId());
            return PayOrderEntity.builder()
                    .orderId(unpaidOrderEntity.getOrderId())
                    .payUrl(unpaidOrderEntity.getPayUrl())
                    .build();
        } else if (null != unpaidOrderEntity && OrderStatusVO.CREATE.equals(unpaidOrderEntity.getOrderStatus())) {
            log.info("创建订单-存在，存在未创建支付单订单，创建支付单开始 userId:{} productId:{} orderId:{}", shopCartEntity.getUserId(), shopCartEntity.getProductId(), unpaidOrderEntity.getOrderId());
            PayOrderEntity payOrderEntity = this.doPrepayOrder(shopCartEntity.getUserId(), shopCartEntity.getProductId(), unpaidOrderEntity.getProductName(), unpaidOrderEntity.getOrderId(), unpaidOrderEntity.getTotalAmount());
            return PayOrderEntity.builder()
                    .orderId(payOrderEntity.getOrderId())
                    .payUrl(payOrderEntity.getPayUrl())
                    .build();
        }

        // 2. 查询商品 & 聚合订单
        ProductEntity productEntity = repository.queryProductByProductId(shopCartEntity.getProductId());
        OrderEntity orderEntity = OrderEntity.builder()
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .orderId(RandomStringUtils.randomNumeric(16))
                .orderTime(new Date())
                .orderStatus(OrderStatusVO.CREATE)
                .build();
        CreateOrderAggregate orderAggregate = CreateOrderAggregate.builder()
                .userId(shopCartEntity.getUserId())
                .productEntity(productEntity)
                .orderEntity(orderEntity)
                .build();

        // 3. 保存订单 - 保存一份订单，再用订单生成ID生成支付单信息
        this.doSaveOrder(orderAggregate);

        // 4. 创建支付单
        PayOrderEntity payOrderEntity = this.doPrepayOrder(shopCartEntity.getUserId(), productEntity.getProductId(), productEntity.getProductName(), orderEntity.getOrderId(), productEntity.getPrice());
        log.info("创建订单-完成，生成支付单。userId: {} orderId: {} payUrl: {}", shopCartEntity.getUserId(), orderEntity.getOrderId(), payOrderEntity.getPayUrl());

        return PayOrderEntity.builder()
                .orderId(payOrderEntity.getOrderId())
                .payUrl(payOrderEntity.getPayUrl())
                .build();
    }

    /**
     * 保存订单
     *
     * @param orderAggregate 订单聚合
     */
    protected abstract void doSaveOrder(CreateOrderAggregate orderAggregate);

    /**
     * 预支付订单生成
     *
     * @param userId      用户ID
     * @param productId   商品ID
     * @param productName 商品名称
     * @param orderId     订单ID
     * @param totalAmount 支付金额
     * @return 预支付订单
     */
    protected abstract PayOrderEntity doPrepayOrder(String userId, String productId, String productName, String orderId, BigDecimal totalAmount) throws AlipayApiException;
}
```
```
@Slf4j
@Service
public class OrderService extends AbstractOrderService{

    @Value("${alipay.notify_url}")
    private String notifyUrl;
    @Value("${alipay.return_url}")
    private String returnUrl;
    @Resource
    private AlipayClient alipayClient;

    @Override
    protected void doSaveOrder(CreateOrderAggregate orderAggregate) {
        repository.doSaveOrder(orderAggregate);
    }

    @Override
    protected PayOrderEntity doPrepayOrder(String userId, String productId, String productName, String orderId, BigDecimal totalAmount) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", totalAmount.toString());
        bizContent.put("subject", productName);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());

        String form = alipayClient.pageExecute(request).getBody();

        PayOrderEntity payOrderEntity = new PayOrderEntity();
        payOrderEntity.setOrderId(orderId);
        payOrderEntity.setPayUrl(form);
        payOrderEntity.setOrderStatus(OrderStatusVO.PAY_WAIT);

        // 更新订单支付信息
        repository.updateOrderPayInfo(payOrderEntity);

        return payOrderEntity;
    }

    @Override
    public void changeOrderPaySuccess(String orderId) {
        repository.changeOrderPaySuccess(orderId);
    }
}
```




