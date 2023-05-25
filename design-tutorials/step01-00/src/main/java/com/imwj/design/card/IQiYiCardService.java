package com.imwj.design.card;

/**
 * 爱奇艺会员卡服务
 * @author wj
 * @create 2023-05-24 17:25
 */
public class IQiYiCardService {

    public void grantToken(String bindMobileNumber, String cardId) {
        System.out.println("模拟发放爱奇艺会员卡一张：" + bindMobileNumber + "，" + cardId);
    }

}
