package com.imwj.domain.service;

import com.google.common.cache.Cache;
import com.imwj.domain.adapter.ILoginAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-04-03 14:36
 * @description 微信服务
 */
@Service
public class WeixinLoginService implements ILoginService{

    @Resource
    private ILoginAdapter loginAdapter;
    @Resource
    private Cache<String, String> openidToken;

    @Override
    public String createQrCodeTicket() {
        try {
            return loginAdapter.creatQrCodeTicket();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String checkLogin(String ticket) {
        // 通过ticket判断，用户是否登录（缓存是否存在）
        return openidToken.getIfPresent(ticket);
    }
}
