package com.imwj.design.service.impl;

import com.imwj.design.service.IVideoUserService;

/**
 * vip用户
 * @author wj
 * @create 2023-05-19 15:30
 */
public class VipUserServiceImpl implements IVideoUserService {

    @Override
    public void definition() {
        System.out.println("VIP用户，视频1080P蓝光");
    }

    @Override
    public void advertisement() {
        System.out.println("VIP会员，视频无广告");
    }
}
