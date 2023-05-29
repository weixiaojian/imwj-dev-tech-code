package com.imwj.design.tets;

import com.imwj.design.CacheClusterServiceImpl;
import com.imwj.design.CacheService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wj
 * @create 2023-05-29 11:37
 */
public class ApiTest {


    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_CacheServiceAfterImpl() {
        CacheService cacheService = new CacheClusterServiceImpl();

        cacheService.set("user_name_01", "imwj", 1);
        String val01 = cacheService.get("user_name_01", 1);
        logger.info("缓存集群升级，测试结果：{}", val01);
    }
}
