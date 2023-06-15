package com.imwj.design;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wj
 * @create 2023-06-15 17:01
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_Editing2Arraignment() {
        String activityId = "100001";
        // 初始化状态
        ActivityService.init(activityId, Status.Editing);
        StateHandler stateHandler = new StateHandler();
        // 获取对应状态操作类 并执行其中的方法
        Result result = stateHandler.arraignment(activityId, Status.Editing);
        logger.info("测试结果(编辑中To提审活动)：{}", JSON.toJSONString(result));
        logger.info("活动信息：{} 状态：{}", JSON.toJSONString(ActivityService.queryActivityInfo(activityId)), JSON.toJSONString(ActivityService.queryActivityInfo(activityId).getStatus()));
    }
}
