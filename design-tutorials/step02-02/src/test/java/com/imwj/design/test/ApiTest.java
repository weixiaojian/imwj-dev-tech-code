package com.imwj.design.test;

import com.imwj.design.application.CacheService;
import com.imwj.design.factory.JDKProxyFactory;
import com.imwj.design.workshop.impl.EGMCacheAdapter;
import com.imwj.design.workshop.impl.IIRCacheAdapter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wj
 * @create 2023-05-29 13:41
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_cacheService() throws Exception {
        CacheService proxy_Gm = JDKProxyFactory.getProxy(CacheService.class, EGMCacheAdapter.class);
        proxy_Gm.set("user_name_01", "imwj");
        String val01 = proxy_Gm.get("user_name_01");
        logger.info("缓存服务 EGM 测试，proxy_EGM.get 测试结果：{}", val01);

        CacheService proxy_IIR = JDKProxyFactory.getProxy(CacheService.class, IIRCacheAdapter.class);
        proxy_IIR.set("user_name_01", "imwj");
        String val02 = proxy_IIR.get("user_name_01");
        logger.info("缓存服务 IIR 测试，proxy_IIR.get 测试结果：{}", val02);
    }

}
