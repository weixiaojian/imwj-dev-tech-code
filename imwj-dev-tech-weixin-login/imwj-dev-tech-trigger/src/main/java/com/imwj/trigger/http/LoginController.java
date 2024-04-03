package com.imwj.trigger.http;

import com.imwj.domain.service.ILoginService;
import com.imwj.types.common.Constants;
import com.imwj.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-04-02 17:47
 * @description 登录controller
 */
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/login/")
public class LoginController {

    @Resource
    ILoginService loginService;

    /**
     * 生成微信扫码登录titcket
     * @return
     */
    @RequestMapping(value = "weixin_qrcode_ticket", method = RequestMethod.GET)
    public Response<String> weixinQrCodeTicket() {
        try{
            String qrCodeTicket = loginService.createQrCodeTicket();
            log.info("生成微信扫码登录 ticket {}", qrCodeTicket);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(qrCodeTicket)
                    .build();
        }catch (Exception e){
            log.error("生成微信扫码登录titcket失败", e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 校验是否登录
     * @param ticket
     * @return
     */
    @RequestMapping(value = "check_login", method = RequestMethod.GET)
    public Response<String> checkLogin(@RequestParam String ticket) {
        try {
            String openidToken = loginService.checkLogin(ticket);
            log.info("扫描检测登录结果 ticket:{} openidToken:{}", ticket, openidToken);
            if (StringUtils.isNotBlank(openidToken)) {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.SUCCESS.getCode())
                        .info(Constants.ResponseCode.SUCCESS.getInfo())
                        .data(openidToken)
                        .build();
            } else {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.NO_LOGIN.getCode())
                        .info(Constants.ResponseCode.NO_LOGIN.getInfo())
                        .build();
            }
        } catch (Exception e) {
            log.info("扫描检测登录结果失败 ticket:{}", ticket);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
