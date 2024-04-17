package com.imwj.ltzf;

import com.imwj.ltzf.factory.Configuration;
import com.imwj.ltzf.factory.PayFactory;
import com.imwj.ltzf.factory.defaults.DefaultPayFactory;
import com.imwj.ltzf.payments.h5.H5PayService;
import com.imwj.ltzf.payments.nativepay.NativePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author wj
 * @create 2024-04-16 17:54
 * @description
 */
@Slf4j
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
