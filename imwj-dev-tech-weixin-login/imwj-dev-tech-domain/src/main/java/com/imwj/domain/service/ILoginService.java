package com.imwj.domain.service;

/**
 * @author wj
 * @create 2024-04-03 14:35
 * @description 微信服务接口
 */
public interface ILoginService {

    /**
     * 创建扫码登录Ticket
     * @return
     */
    String createQrCodeTicket();

    /**
     * 校验是否登录
     * @param ticket
     * @return
     */
    String checkLogin(String ticket);

}
