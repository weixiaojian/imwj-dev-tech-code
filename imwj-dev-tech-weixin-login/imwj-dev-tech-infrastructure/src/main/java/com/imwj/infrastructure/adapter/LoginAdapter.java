package com.imwj.infrastructure.adapter;

import com.google.common.cache.Cache;
import com.imwj.domain.adapter.ILoginAdapter;
import com.imwj.infrastructure.geteway.IWeixinApiService;
import com.imwj.infrastructure.geteway.dto.WeixinQrCodeRequestDTO;
import com.imwj.infrastructure.geteway.dto.WeixinQrCodeResponseDTO;
import com.imwj.infrastructure.geteway.dto.WeixinTokenResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-04-03 15:03
 * @description
 */
@Service
public class LoginAdapter implements ILoginAdapter {

    @Value("${weixin.config.app-id}")
    private String appid;
    @Value("${weixin.config.app-secret}")
    private String appSecret;
    @Resource
    private Cache<String, String> weixinAccessToken;
    @Resource
    private IWeixinApiService weixinApiService;

    @Override
    public String creatQrCodeTicket() throws Exception {
        // 1.获取accessToken
        String accessToken = weixinAccessToken.getIfPresent(appid);
        if(null == accessToken){
            Call<WeixinTokenResponseDTO> call = weixinApiService.getToken("client_credential", appid, appSecret);
            WeixinTokenResponseDTO weixinTokenResponseDTO = call.execute().body();
            accessToken = weixinTokenResponseDTO.getAccess_token();
            weixinAccessToken.put(appid, accessToken);
        }
        // 2.生成ticket
        WeixinQrCodeRequestDTO request = WeixinQrCodeRequestDTO.builder()
                .expire_seconds(2592000) // 过期时间单位为秒 2592000 = 30天
                .action_name(WeixinQrCodeRequestDTO.ActionNameTypeVO.QR_SCENE.getCode())
                .action_info(WeixinQrCodeRequestDTO.ActionInfo.builder()
                        .scene(WeixinQrCodeRequestDTO.ActionInfo.Scene.builder()
                                .scene_id(100601) // 场景ID
                                // .scene_str("test") 配合 ActionNameTypeVO.QR_STR_SCENE
                                .build())
                        .build())
                .build();

        Call<WeixinQrCodeResponseDTO> qrCodeCall = weixinApiService.createQrCode(accessToken, request);
        WeixinQrCodeResponseDTO weixinQrCodeResponseDTO = qrCodeCall.execute().body();
        return weixinQrCodeResponseDTO.getTicket();
    }
}
