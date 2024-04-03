package com.imwj.config;

import com.imwj.infrastructure.geteway.IWeixinApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author wj
 * @create 2024-04-02 17:35
 * @description Retrofit2配置
 */
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

