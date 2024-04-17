# 蓝兔支付sdk开发
## SDK编写
* 1.编写Retrofit2-http请求接口
```
/**
 * @author wj
 * @create 2024-04-16 16:44
 * @description 扫码支付接口调用(Retrofit2),文档地址https://www.ltzf.cn/doc
 */
public interface INativePayApi {


    /**
     * 扫码支付
     * @param mchId 商户id
     * @param outTradeNo 订单好
     * @param totalFee 总金额
     * @param body 内容
     * @param timestamp 时间戳
     * @param notifyUrl 回调地址
     * @param sign 签名
     * @return
     */
    @POST("api/wxpay/native")
    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    Call<PrepayResponse> prepay(@Field("mch_id") String mchId,
                                @Field("out_trade_no") String outTradeNo,
                                @Field("total_fee") String totalFee,
                                @Field("body") String body,
                                @Field("timestamp") String timestamp,
                                @Field("notify_url") String notifyUrl,
                                @Field("sign") String sign);
}
```

* 2.支付调用业务NativePayService
```
/**
 * @author wj
 * @create 2024-04-16 16:49
 * @description 扫码支付service
 */
public class NativePayService {

    private final Configuration configuration;
    private final INativePayApi nativePayApi;


    public NativePayService(INativePayApi nativePayApi, Configuration configuration) {
        this.configuration = configuration;
        this.nativePayApi = nativePayApi;
    }

    /**
     * 扫码支付
     * @param request
     * @return
     * @throws Exception
     */
    public PrepayResponse prepay(PrepayRequest request) throws Exception {
        // 1.请求接口
        Call<PrepayResponse> call = nativePayApi.prepay(
                request.getMchid(),
                request.getOutTradeNo(),
                request.getTotalFee(),
                request.getBody(),
                request.getTimestamp(),
                request.getNotifyUrl(),
                request.createSign(configuration.getPartnerKey()));
        // 2.返回数据
        Response<PrepayResponse> execute = call.execute();
        //3.返回结果
        return execute.body();
    }
}
```

* 3.工厂模式接口PayFactory
```
public interface PayFactory {

    NativePayService nativePayService();

    H5PayService h5PayService();

    AppPayService appPayService();

    JSPayService jsPayService();

    JumpH5PayService jumpH5PayService();
}
```

* 4.默认的创建工厂DefaultPayFactory
```

/**
 * @author wj
 * @create 2024-04-16 17:25
 * @description 默认支付工厂(创建支付类)
 */
public class DefaultPayFactory implements PayFactory {

    private final Configuration configuration;
    private final OkHttpClient httpClient;


    public DefaultPayFactory(Configuration configuration) {
        this.configuration = configuration;
        // 1.配置日志
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 2.开启Http客户端配置
        this.httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(configuration.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(configuration.getWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(configuration.getReadTimeout(), TimeUnit.SECONDS)
                .build();
    }


    @Override
    public NativePayService nativePayService() {
        // 1.构建api
        INativePayApi nativePayApi = new Retrofit.Builder()
                .baseUrl(configuration.getApiHost())
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(INativePayApi.class);
        // 2.创建支付service
        return new NativePayService(nativePayApi, configuration);
    }
}
```

* 5.测试用例
```
/**
 * @author wj
 * @create 2024-04-16 17:34
 * @description 扫码支付service测试
 */
@Slf4j
public class PayServiceTest {

    private NativePayService nativePayService;


    @Before
    public void init(){
        // 1.初始化配置类(appid、merchantId、partnerKey)
        Configuration configuration = new Configuration("1104268", "1673424392", "6d3e889f359fcb83d150e9553a9217b9");
        // 2.初始化默认的创建工厂(http客户端配置:请求超时时间等)
        DefaultPayFactory payFactory = new DefaultPayFactory(configuration);
        // 3.初始化Retrofit(baseUrl)、创建nativePayService
        this.nativePayService = payFactory.nativePayService();
    }

    @Test
    public void test_prepay() throws Exception{
        // 4.封装请求参数
        PrepayRequest request = new PrepayRequest();
        request.setMchid("1673424392");
        request.setOutTradeNo(RandomStringUtils.randomNumeric(8));
        request.setTotalFee("0.01");
        request.setBody("IPHONE 15 PRO MAX");
        request.setNotifyUrl("https://bolg.imwj.club/");

        // 5.创建支付订单
        log.info("请求参数:{}", JSON.toJSONString(request));
        PrepayResponse response = nativePayService.prepay(request);
        log.info("响应参数:{}", JSON.toJSONString(response));
    }
}
```

## SpringBoot使用
* 1.编写的SDK打包到本地mvn仓库,spring boot项目直接通过mvn地址引入
* 2.支付配置yml文件映射
```
@Data
@ConfigurationProperties(prefix = "ltzf.sdk.config", ignoreInvalidFields = true)
public class LtzfSDKConfigProperties {

    /** 状态；open = 开启、close 关闭 */
    private boolean enable;
    /** 开发者ID */
    private String appId;
    /** 商户号ID */
    private String merchantId;
    /** 商户秘钥 */
    private String partnerKey;
}
```
* 3.支付配置类,通过下面三个方法将支付service配置到spring中
```
/**
 * @author wj
 * @create 2024-04-16 17:54
 * @description
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(LtzfSDKConfigProperties.class)
public class LtzfSDKConfig {

    @Bean(name = "payFactory")
    @ConditionalOnProperty(value = "ltzf.sdk.config.enabled", havingValue = "true", matchIfMissing = false)
    public PayFactory payFactory(LtzfSDKConfigProperties properties) {
        Configuration configuration = new Configuration(
                properties.getAppId(),
                properties.getMerchantId(),
                properties.getPartnerKey()
        );
        return new DefaultPayFactory(configuration);
    }

    @Bean(name = "nativePayService")
    @ConditionalOnProperty(value = "ltzf.sdk.config.enabled", havingValue = "true", matchIfMissing = false)
    public NativePayService nativePayService(PayFactory payFactory) {
        log.info("蓝兔支付 SDK 启动成功，扫码支付服务已装配");
        return payFactory.nativePayService();
    }

    @Bean(name = "h5PayService")
    @ConditionalOnProperty(value = "ltzf.sdk.config.enabled", havingValue = "true", matchIfMissing = false)
    public H5PayService h5PayService(PayFactory payFactory) {
        log.info("蓝兔支付 SDK 启动成功，H5支付服务已装配");
        return payFactory.h5PayService();
    }
}
```