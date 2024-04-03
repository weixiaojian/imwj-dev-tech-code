package com.imwj.infrastructure.geteway;

import com.imwj.infrastructure.geteway.dto.WeixinQrCodeRequestDTO;
import com.imwj.infrastructure.geteway.dto.WeixinQrCodeResponseDTO;
import com.imwj.infrastructure.geteway.dto.WeixinTokenResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author wj
 * @create 2024-04-02 17:23
 * @description 微信api服务
 */
public interface IWeixinApiService {

    /**
     * 获取Access token
     * @param grantType
     * @param appId
     * @param appSecret
     * @return
     */
    @GET("cgi-bin/token")
    Call<WeixinTokenResponseDTO> getToken(
            @Query("grant_type") String grantType,
            @Query("appid") String appId,
            @Query("secret") String appSecret
    );

    /**
     * 获取凭据 ticket
     * @param accessToken
     * @param weixinQrCodeRequestDTO
     * @return
     */
    @POST("cgi-bin/qrcode/create")
    Call<WeixinQrCodeResponseDTO> createQrCode(@Query("access_token") String accessToken, @Body WeixinQrCodeRequestDTO weixinQrCodeRequestDTO);

}
