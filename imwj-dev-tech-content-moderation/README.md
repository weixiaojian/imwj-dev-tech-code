# 内容敏感词过滤 
# Retrofit2-Http客户端库
> 将HTTP API转换为Java接口，并且可以通过注解来定义请求参数、请求方法、请求头等信息。Retrofit2使得网络请求变得简单和直观，同时提供了强大的功能，如异步请求、同步请求、文件上传、文件下载等
* 1.maven依赖
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
* 2.定义一个请求接口类
```
public interface INativePayApi {

    @POST("api/wxpay/native")
    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    Call<Object> prepay(@Field("mch_id") String mchId,
                        @Field("out_trade_no") String outTradeNo,
                        @Field("total_fee") String totalFee,
                        @Field("body") String body,
                        @Field("timestamp") String timestamp,
                        @Field("notify_url") String notifyUrl,
                        @Field("sign") String sign
                        );

}
```
* 3.调用接口，实现HTTP请求
```
    @Test
    public void test_retrofit2() throws IOException {
        // 1.初始化客户端
        OkHttpClient httpClient = new OkHttpClient();
        INativePayApi nativePayApi = new Retrofit.Builder()
                .baseUrl("https://api.ltzf.cn/")
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(INativePayApi.class);
        // 2.封装请求参数
        Call<Object> call = nativePayApi.prepay(
                "1673424392",
                "imwj20240415",
                "0.01",
                "IPHONE 15 PRO MAX",
                String.valueOf(System.currentTimeMillis() / 1000),
                        "http://blog.imwj.club",
                        "6d3e889f359fcb83d150e9553a9217b9");
        // 3.调用接口 得到结果
        Response<Object> response = call.execute();
        Object object = response.body();

        log.info("测试结果:{}", JSON.toJSONString(object));
    }
```

# Retrofit2复杂使用
* 1.参数配置`Configuration`
```
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {

    @Getter
    @NotNull
    private String apiKey;

    @Getter
    private String apiHost;

    @Getter
    @NotNull
    private String authToken;
}
```
* 2.拦截器类`OpenAiInterceptor`
```
public class OpenAiInterceptor implements Interceptor {

    /** OpenAi apiKey 需要在官网申请 */
    private String apiKey;
    /** 访问授权接口的认证 Token */
    private String authToken;

    public OpenAiInterceptor(String apiKey, String authToken) {
        this.apiKey = apiKey;
        this.authToken = authToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(this.auth(apiKey, chain.request()));
    }

    /**
     * 登录
     * @param apiKey
     * @param original
     * @return
     */
    private Request auth(String apiKey, Request original) {
        // 设置token信息（如果没有此类限制 可以不设置）
        HttpUrl url = original.url().newBuilder()
                .addQueryParameter("token", authToken)
                .build();
        // 创建请求
        return original.newBuilder()
                .url(url)
                .header(Header.AUTHORIZATION.getValue(), "Bearer " + apiKey)
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .method(original.method(), original.body())
                .build();
    }
}
```
* 3.默认的会话工厂`DefaultOpenAiSessionFactory`
```
public class DefaultOpenAiSessionFactory implements OpenAiSessionFactory {

    private final Configuration configuration;

    public DefaultOpenAiSessionFactory(Configuration configuration) {
        // 初始化时赋值config配置
        this.configuration = configuration;
    }

    @Override
    public OpenAiSession openSession() {
        // 1.日志配置
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        // 2.开启Http客户端（设置日志拦截器、登录拦截器OpenAiInterceptor[赋值token]）
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new OpenAiInterceptor(configuration.getApiKey(), configuration.getAuthToken()))
                .connectTimeout(450, TimeUnit.SECONDS)
                .writeTimeout(450, TimeUnit.SECONDS)
                .readTimeout(450, TimeUnit.SECONDS)
                .build();

        // 3.创建API服务
        IOpenAiApi openAiApi = new Retrofit.Builder()
                .baseUrl(configuration.getApiHost())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(IOpenAiApi.class);
        return new DefaultOpenAiSession(openAiApi);
    }
}
```
* 4.默认的会话类`[DefaultOpenAiSession.java](chatgpt-sdk%2Fsrc%2Fmain%2Fjava%2Fcom%2Fimw%2Fsdk%2Fsession%2Fdefaults%2FDefaultOpenAiSession.java)`
```
public class DefaultOpenAiSession implements OpenAiSession {

    private final IOpenAiApi openAiApi;

    public DefaultOpenAiSession(IOpenAiApi openAiApi){
        this.openAiApi = openAiApi;
    }

    @Override
    public QACompletionResponse completions(QACompletionRequest qaCompletionRequest) {
        return openAiApi.completions(qaCompletionRequest).blockingGet();
    }

    @Override
    public QACompletionResponse completions(String question) {
        QACompletionRequest qaCompletionRequest = QACompletionRequest
                .builder()
                .prompt(question)
                .build();
        return openAiApi.completions(qaCompletionRequest).blockingGet();
    }

    @Override
    public ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest) {
        return openAiApi.completions(chatCompletionRequest).blockingGet();
    }
}
```
* 5.最终请求api`IOpenAiApi`
```
public interface IOpenAiApi {

    /**
     * 文本问答
     * @param qaCompletionRequest 请求信息
     * @return                    返回结果
     */
    @POST("v1/completions")
    Single<QACompletionResponse> completions(@Body QACompletionRequest qaCompletionRequest);

    /**
     * 默认 GPT-3.5 问答模型
     * @param chatCompletionRequest 请求信息
     * @return                      返回结果
     */
    @POST("v1/chat/completions")
    Single<ChatCompletionResponse> completions(@Body ChatCompletionRequest chatCompletionRequest);
}
```
* 6.测试验证
```
public class ApiTest {

    private OpenAiSession openAiSession;

    @Before
    public void test_OpenAiSessionFactory() {
        // 1. 配置文件
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://api.openai.com/");
        configuration.setApiKey("sk-test");
        // 2. 会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        // 3. 开启会话
        this.openAiSession = factory.openSession();
    }

    /**
     * 简单问答模式
     */
    @Test
    public void test_qa_completions() throws JsonProcessingException {
        QACompletionResponse response01 = openAiSession.completions("写个java冒泡排序");
        log.info("测试结果：{}", new ObjectMapper().writeValueAsString(response01.getChoices()));
    }

    /**
     * 此对话模型 3.5 接近于官网体验
     */
    @Test
    public void test_chat_completions() {
        // 1. 创建参数
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();
        // 2. 发起请求
        ChatCompletionResponse chatCompletionResponse = openAiSession.completions(chatCompletion);
        // 3. 解析结果
        chatCompletionResponse.getChoices().forEach(e -> {
            log.info("测试结果：{}", e.getMessage());
        });
    }

}
```
* 7.请求路线解析：配置configuration > 配置拦截器OpenAiInterceptor > 创建DefaultOpenAiSessionFactory（开启Http客户端/添加拦截器/创建API服务）> 得到DefaultOpenAiSession会话 > 发送请求IOpenAiApi