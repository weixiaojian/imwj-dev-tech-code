package com.imwj.ltzf.factory.defaults;

import com.imwj.ltzf.factory.Configuration;
import com.imwj.ltzf.factory.PayFactory;
import com.imwj.ltzf.payments.app.AppPayService;
import com.imwj.ltzf.payments.h5.H5PayService;
import com.imwj.ltzf.payments.jsp.JSPayService;
import com.imwj.ltzf.payments.jumph5.JumpH5PayService;
import com.imwj.ltzf.payments.nativepay.INativePayApi;
import com.imwj.ltzf.payments.nativepay.NativePayService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

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

    @Override
    public H5PayService h5PayService() {
        return null;
    }

    @Override
    public AppPayService appPayService() {
        return null;
    }

    @Override
    public JSPayService jsPayService() {
        return null;
    }

    @Override
    public JumpH5PayService jumpH5PayService() {
        return null;
    }
}
