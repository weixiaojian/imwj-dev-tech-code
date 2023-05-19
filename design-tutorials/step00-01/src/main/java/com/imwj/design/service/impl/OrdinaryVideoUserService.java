package com.imwj.design.service.impl;

import com.imwj.design.service.IVideoUserService;

/**
 * 普通用户
 * @author wj
 * @create 2023-05-19 15:32
 */
public class OrdinaryVideoUserService implements IVideoUserService {
    @Override
    public void definition() {
        System.out.println("普通用户，720高清");
    }

    @Override
    public void advertisement() {
        System.out.println("普通用户，90秒广告");
    }
}
