package com.imwj.ltzf.factory;

import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author wj
 * @create 2024-04-16 17:17
 * @description
 */
@Getter
public class Configuration {

    @Setter
    private String apiHost = "https://api.ltzf.cn/";

    // 开发者ID
    private final String appId;
    // 商户号ID
    private final String merchantId;
    // 商户秘钥
    private final String partnerKey;

    public Configuration(String appId, String merchantId, String partnerKey) {
        this.appId = appId;
        this.merchantId = merchantId;
        this.partnerKey = partnerKey;
    }

    @Setter
    private HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.HEADERS;
    @Setter
    private long connectTimeout = 60;
    @Setter
    private long writeTimeout = 60;
    @Setter
    private long readTimeout = 60;

}
