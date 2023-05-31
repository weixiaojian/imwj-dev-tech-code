package com.imwj.design;

import com.alibaba.fastjson.JSON;
import com.imwj.design.mq.create_account;

/**
 * 创建账号service
 * @author wj
 * @create 2023-05-31 14:38
 */
public class create_accountMqService {

    public void onMessage(String message) {

        create_account mq = JSON.parseObject(message, create_account.class);

        mq.getNumber();
        mq.getAccountDate();

        // ... 处理自己的业务
    }
}
