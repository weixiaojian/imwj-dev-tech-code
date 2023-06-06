package com.imwj.design.test;

import com.alibaba.fastjson.JSON;
import com.imwj.design.Activity;
import com.imwj.design.ActivityController;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wj
 * @create 2023-06-06 11:31
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    private ActivityController activityController = new ActivityController();

    @Test
    public void test() throws InterruptedException {
        for(int i=0; i<10; i++){
            Long req = 1001L;
            Activity activity = activityController.queryActivityInfo(req);
            logger.info("测试结果：{} {}", req, JSON.toJSONString(activity));
            Thread.sleep(1000);
        }
    }
}
