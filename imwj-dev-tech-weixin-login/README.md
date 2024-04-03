# 微信公众号平台扫码登录（DDD）
![image](https://github.com/weixiaojian/study-code/blob/master/imwj-dev-tech-weixin-login/docs/img/imwj-dev-tech-weixin-login.png?raw=true)

## 对接文档-公众号
* 微信测试号：https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index
* 微信公众号文档：https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html

## 工程结构
* imwj-dev-tech-app：是启动应用程序的入口，其他模块也被直接或者间接的引入到 app 模块下，这样才能被 Spring 扫描加载。
* imwj-dev-tech-infrastructure：是基础设施层，用于对接外部接口、缓存、数据库等相关内容的连接使用。本节主要是对接微信开发平台的接口。采用的是 retrofit2 技术框架，这样对接起来更加方便。
* imwj-dev-tech-domain：是功能实现层，像是登录的具体实现，就是在 domain 领域层实现的。你将来使用 DDD 做的其他功能，也是放到 domain 领域下实现，每一个功能就是就是一个模块。
* imwj-dev-tech-types：用于定义基本的类型、枚举、错误码等内容。


## Guava缓存
> 用于存储键值对、获取值、移除键值对等。通过配置可以创建不同特性的Cache实例，如设置缓存大小、过期时间、缓存加载方式等。

* 1.引入依赖
```
            <dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>32.1.2-jre</version>
            </dependency>
```

* 2.缓存配置类
```
@Slf4j
@Configuration
public class GuavaConfig {

    /**
     * weixinAccessToken缓存实例（2小时过期）
     * @return
     */
    @Bean(name = "weixinAccessToken")
    public Cache<String, String> weixinAccessToken() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build();
    }

    /**
     * openidToken实例（1小时过期）
     * @return
     */
    @Bean(name = "openidToken")
    public Cache<String, String> openidToken() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }
}
```

* 3.使用缓存
```
@Service
public class WeixinLoginService implements ILoginService{

    @Resource
    private Cache<String, String> openIdToken;

    @Override
    public String checkLogin(String ticket) {
        // 通过ticket判断，用户是否登录（缓存是否存在）
        return openIdToken.getIfPresent(ticket);
    }
}
```

## Retrofit2客户端
> 基于 Java 的 RESTful HTTP 网络请求库，它可以让你以声明式的方式定义 HTTP 请求，并将 HTTP 响应转换为可用的 Java 对象。
* 1引入依赖
```
        <dependency>
            <groupId>com.squareup.retrofit2</groupId>
            <artifactId>retrofit</artifactId>
            <version>2.9.0</version>
        </dependency>
        <dependency>
            <groupId>com.squareup.retrofit2</groupId>
            <artifactId>converter-jackson</artifactId>
            <version>2.9.0</version>
        </dependency>
        <dependency>
            <groupId>com.squareup.retrofit2</groupId>
            <artifactId>adapter-rxjava2</artifactId>
            <version>2.9.0</version>
        </dependency>
```

* 2.客户端配置类
```
@Slf4j
@Configuration
public class Retrofit2Config {

    private static final String BASE_URL = "https://api.weixin.qq.com/";

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Bean
    public IWeixinApiService weixinApiService(Retrofit retrofit) {
        return retrofit.create(IWeixinApiService.class);
    }

}
```

* 3.接口请求
```
public interface IWeixinApiService {

    /**
     * 获取Access token
     * @param grantType
     * @param appId
     * @param appSecret
     * @return
     */
    @GET("cgi-bin/token")
    Call<WeixinTokenResponseDTO> getToken(
            @Query("grant_type") String grantType,
            @Query("appid") String appId,
            @Query("secret") String appSecret
    );

    /**
     * 获取凭据 ticket
     * @param accessToken
     * @param weixinQrCodeRequestDTO
     * @return
     */
    @POST("cgi-bin/qrcode/create")
    Call<WeixinQrCodeResponseDTO> createQrCode(@Query("access_token") String accessToken, @Body WeixinQrCodeRequestDTO weixinQrCodeRequestDTO);

}
```

* 4.调用接口`weixinApiService`
```
@Service
public class LoginAdapter implements ILoginAdapter {

    @Value("${weixin.config.app-id}")
    private String appid;
    @Value("${weixin.config.app-secret}")
    private String appSecret;
    @Resource
    private Cache<String, String> weixinAccessToken;
    @Resource
    private IWeixinApiService weixinApiService;

    @Override
    public String creatQrCodeTicket() throws Exception {
        // 1.获取accessToken
        String accessToken = weixinAccessToken.getIfPresent(appid);
        if(null == accessToken){
            Call<WeixinTokenResponseDTO> call = weixinApiService.getToken("client_credential", appid, appSecret);
            WeixinTokenResponseDTO weixinTokenResponseDTO = call.execute().body();
            accessToken = weixinTokenResponseDTO.getAccess_token();
            weixinAccessToken.put(appid, accessToken);
        }
    }
}
```

## 微信扫码登录验证
> 源码地址：https://github.com/weixiaojian/study-code/tree/master/imwj-dev-tech-weixin-login

* 0.配置微信公众号验签/回调接口：/api/v1/weixin/portal/receive
```
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/weixin/portal/")
public class WeixinPortalController {

    @Value("${weixin.config.originalid}")
    private String originalid;
    @Resource
    private Cache<String, String> openidToken;

    /**
     * 微信验签 token:langao
     */
    @GetMapping(value = "receive", produces = "text/plain;charset=utf-8")
    public String validate(@RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {
        try {
            log.info("微信公众号验签信息开始 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            boolean check = SignatureUtil.check("langao", signature, timestamp, nonce);
            log.info("微信公众号验签信息完成 check：{}", check);
            if (!check) {
                return null;
            }
            return echostr;
        }catch (Exception e){
            log.error("微信公众号验签信息失败 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr, e);
            return null;
        }
    }

    /**
     * 微信回调
     */
    @PostMapping(value = "receive", produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        try {
            log.info("接收微信公众号信息请求{}开始 {}", openid, requestBody);
            // 消息转换
            MessageTextEntity message = XmlUtil.xmlToBean(requestBody, MessageTextEntity.class);
            // 扫码登录【消息类型和事件】
            if ("event".equals(message.getMsgType()) && "SCAN".equals(message.getEvent())) {
                // 实际的业务场景，可以生成 jwt 的 token 让前端存储
                openidToken.put(message.getTicket(), openid);
                return buildMessageTextEntity(openid, "登录成功");
            }
            log.info("接收微信公众号信息请求{}完成 {}", openid, requestBody);
            return buildMessageTextEntity(openid, "测试本案例，需要请扫码登录！");
        }catch (Exception e){
            log.error("接收微信公众号信息请求{}失败 {}", openid, requestBody, e);
            return "";
        }
    }

    /**
     * 实体转xml字符串
     */
    private String buildMessageTextEntity(String openid, String content) {
        MessageTextEntity res = new MessageTextEntity();
        // 公众号分配的ID
        res.setFromUserName(originalid);
        res.setToUserName(openid);
        res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
        res.setMsgType("text");
        res.setContent(content);
        return XmlUtil.beanToXml(res);
    }
}
```

* 1.获取ticket：/api/v1/login/weixin_qrcode_ticket
```
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/login/")
public class LoginController {

    @Resource
    ILoginService loginService;

    /**
     * 生成微信扫码登录titcket
     * @return
     */
    @RequestMapping(value = "weixin_qrcode_ticket", method = RequestMethod.GET)
    public Response<String> weixinQrCodeTicket() {
        try{
            String qrCodeTicket = loginService.createQrCodeTicket();
            log.info("生成微信扫码登录 ticket {}", qrCodeTicket);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(qrCodeTicket)
                    .build();
        }catch (Exception e){
            log.error("生成微信扫码登录titcket失败", e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
```

* 2.前端根据获取到的ticket生成二维码：https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=填充ticket

* 3.手机微信扫描二维码，会触发微信回调：/api/v1/weixin/portal/receive

* 4.前端轮询调用校验接口校验用户是否关注公众号：/api/v1/login/check_login
```
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/login/")
public class LoginController {

    @Resource
    ILoginService loginService;

    /**
     * 校验是否登录
     * @param ticket
     * @return
     */
    @RequestMapping(value = "check_login", method = RequestMethod.GET)
    public Response<String> checkLogin(@RequestParam String ticket) {
        try {
            String openidToken = loginService.checkLogin(ticket);
            log.info("扫描检测登录结果 ticket:{} openidToken:{}", ticket, openidToken);
            if (StringUtils.isNotBlank(openidToken)) {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.SUCCESS.getCode())
                        .info(Constants.ResponseCode.SUCCESS.getInfo())
                        .data(openidToken)
                        .build();
            } else {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.NO_LOGIN.getCode())
                        .info(Constants.ResponseCode.NO_LOGIN.getInfo())
                        .build();
            }
        } catch (Exception e) {
            log.info("扫描检测登录结果失败 ticket:{}", ticket);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
```