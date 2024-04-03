package com.imwj.trigger.http;

import com.google.common.cache.Cache;
import com.imwj.types.sdk.weixin.MessageTextEntity;
import com.imwj.types.sdk.weixin.SignatureUtil;
import com.imwj.types.sdk.weixin.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-04-03 14:47
 * @description 微信对接服务
 */
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/weixin/portal/")
public class WeixinPortalController {

    @Value("${weixin.config.originalid}")
    private String originalid;
    @Resource
    private Cache<String, String> openidToken;

    /**
     * 微信验签 token:langao
     */
    @GetMapping(value = "receive", produces = "text/plain;charset=utf-8")
    public String validate(@RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {
        try {
            log.info("微信公众号验签信息开始 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            boolean check = SignatureUtil.check("langao", signature, timestamp, nonce);
            log.info("微信公众号验签信息完成 check：{}", check);
            if (!check) {
                return null;
            }
            return echostr;
        }catch (Exception e){
            log.error("微信公众号验签信息失败 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr, e);
            return null;
        }
    }

    /**
     * 微信回调
     */
    @PostMapping(value = "receive", produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        try {
            log.info("接收微信公众号信息请求{}开始 {}", openid, requestBody);
            // 消息转换
            MessageTextEntity message = XmlUtil.xmlToBean(requestBody, MessageTextEntity.class);
            // 扫码登录【消息类型和事件】
            if ("event".equals(message.getMsgType()) && "SCAN".equals(message.getEvent())) {
                // 实际的业务场景，可以生成 jwt 的 token 让前端存储
                openidToken.put(message.getTicket(), openid);
                return buildMessageTextEntity(openid, "登录成功");
            }
            log.info("接收微信公众号信息请求{}完成 {}", openid, requestBody);
            return buildMessageTextEntity(openid, "测试本案例，需要请扫码登录！");
        }catch (Exception e){
            log.error("接收微信公众号信息请求{}失败 {}", openid, requestBody, e);
            return "";
        }
    }

    /**
     * 实体转xml字符串
     */
    private String buildMessageTextEntity(String openid, String content) {
        MessageTextEntity res = new MessageTextEntity();
        // 公众号分配的ID
        res.setFromUserName(originalid);
        res.setToUserName(openid);
        res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
        res.setMsgType("text");
        res.setContent(content);
        return XmlUtil.beanToXml(res);
    }
}
