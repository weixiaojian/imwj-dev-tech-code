package com.imwj.design.test;

import com.alibaba.fastjson.JSON;
import com.imwj.design.MQAdapter;
import com.imwj.design.OrderAdapterService;
import com.imwj.design.RebateInfo;
import com.imwj.design.impl.InsideOrderServiceImpl;
import com.imwj.design.impl.POPOrderAdapterServiceImpl;
import com.imwj.design.mq.create_account;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

/**
 * @author wj
 * @create 2023-05-31 15:03
 */
public class ApiTest {

    @Test
    public void test() throws Exception {
        create_account create_account = new create_account();
        create_account.setNumber("100001");
        create_account.setAddress("河北省.廊坊市.广阳区.大学里职业技术学院");
        create_account.setAccountDate(new Date());
        create_account.setDesc("在校开户");

        HashMap<String, String> link01 = new HashMap<String, String>();
        link01.put("userId", "number");
        link01.put("bizId", "number");
        link01.put("desc", "desc");
        RebateInfo rebateInfo01 = MQAdapter.filter(JSON.toJSONString(create_account), link01);
        System.out.println("mq.create_account(适配前)" + create_account.toString());
        System.out.println("mq.create_account(适配后)" + JSON.toJSONString(rebateInfo01));

        System.out.println("======service适配（实现同一个接口即可）===========");
        OrderAdapterService popOrderAdapterService = new POPOrderAdapterServiceImpl();
        System.out.println("判断首单，接口适配(POP)：" + popOrderAdapterService.isFirst("100001"));

        OrderAdapterService insideOrderService = new InsideOrderServiceImpl();
        System.out.println("判断首单，接口适配(自营)：" + insideOrderService.isFirst("100001"));
    }

}
