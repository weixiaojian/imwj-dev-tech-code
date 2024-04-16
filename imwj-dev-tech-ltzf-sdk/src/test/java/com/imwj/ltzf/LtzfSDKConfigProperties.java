package com.imwj.ltzf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wj
 * @create 2024-04-16 17:54
 * @description
 */
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