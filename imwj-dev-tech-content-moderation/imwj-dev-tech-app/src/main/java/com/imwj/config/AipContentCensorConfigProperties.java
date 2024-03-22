package com.imwj.config;

import com.baidu.aip.contentcensor.AipContentCensor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wj
 * @create 2024-03-20 17:30
 * @description
 */
@Data
@ConfigurationProperties(prefix = "baidu.aip", ignoreInvalidFields = true)
public class AipContentCensorConfigProperties {

    private String app_id;
    private String api_key;
    private String secret_key;

}

