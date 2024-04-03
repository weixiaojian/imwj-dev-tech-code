package com.imwj.infrastructure.geteway.dto;

import lombok.Data;

/**
 * @author wj
 * @create 2024-04-02 17:23
 * 获取微信登录二维码响应对象
 */
@Data
public class WeixinQrCodeResponseDTO {

    private String ticket;
    private Long expire_seconds;
    private String url;

}
