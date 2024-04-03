package com.imwj.infrastructure.geteway.dto;

import lombok.Data;

/**
 * @author wj
 * @create 2024-04-02 17:23
 * 获取 Access token DTO 对象
 */
@Data
public class WeixinTokenResponseDTO {

    private String access_token;
    private int expires_in;
    private String errcode;
    private String errmsg;

}
