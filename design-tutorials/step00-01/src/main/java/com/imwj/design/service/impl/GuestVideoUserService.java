package com.imwj.design.service.impl;

import com.imwj.design.service.IVideoUserService;

/**
 * 访客用户
 * @author wj
 * @create 2023-05-19 15:31
 */
public class GuestVideoUserService implements IVideoUserService {
    @Override
    public void definition() {
        System.out.println("访客用户，480标清");
    }

    @Override
    public void advertisement() {
        System.out.println("访客用户，120秒广告");
    }
}
