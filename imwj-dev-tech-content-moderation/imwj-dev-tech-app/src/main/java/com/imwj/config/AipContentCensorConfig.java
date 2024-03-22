package com.imwj.config;

import com.baidu.aip.contentcensor.AipContentCensor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wj
 * @create 2024-03-20 17:30
 * @description
 */
@Configuration
@EnableConfigurationProperties(AipContentCensorConfigProperties.class)
public class AipContentCensorConfig {

    @Bean
    public AipContentCensor aipContentCensor(AipContentCensorConfigProperties properties) {
        AipContentCensor client = new AipContentCensor(properties.getApp_id(), properties.getApi_key(), properties.getSecret_key());
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }

}
