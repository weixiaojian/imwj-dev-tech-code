package com.imwj.design.test;

import com.alibaba.fastjson.JSON;
import com.imwj.design.BetUser;
import com.imwj.design.DrawControl;
import com.imwj.design.DrawRandom;
import com.imwj.design.DrawWeightRank;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @create 2023-05-22 15:38
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_DrawControl() {
        List<BetUser> betUserList = new ArrayList<>();
        betUserList.add(new BetUser("花花", 65));
        betUserList.add(new BetUser("豆豆", 43));
        betUserList.add(new BetUser("小白", 72));
        betUserList.add(new BetUser("笨笨", 89));
        betUserList.add(new BetUser("丑蛋", 10));

        DrawControl drawControl = new DrawControl();
        List<BetUser> prizeRandomUserList = drawControl.doDraw(new DrawRandom(), betUserList, 3);
        logger.info("随机抽奖，中奖用户名单：{}", JSON.toJSON(prizeRandomUserList));

        List<BetUser> prizeWeightUserList = drawControl.doDraw(new DrawWeightRank(), betUserList, 3);
        logger.info("权重抽奖，中奖用户名单：{}", JSON.toJSON(prizeWeightUserList));
    }
}
