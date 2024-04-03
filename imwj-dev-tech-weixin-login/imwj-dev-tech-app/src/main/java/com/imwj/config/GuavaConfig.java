package com.imwj.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author wj
 * @create 2024-04-03 14:49
 * @description com.google.common.cache.Cache缓存配置
 */
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
