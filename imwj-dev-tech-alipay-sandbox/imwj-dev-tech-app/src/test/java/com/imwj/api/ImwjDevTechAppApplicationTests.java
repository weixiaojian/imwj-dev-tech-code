package com.imwj.api;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ImwjDevTechAppApplicationTests {


	// 「沙箱环境」应用ID - 您的APPID，收款账号既是你的APPID对应支付宝账号。获取地址；https://open.alipay.com/develop/sandbox/app
	public static String app_id = "9021000134629874";
	// 「沙箱环境」商户私钥，你的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC6asle5Xw8aCYCIxEGGag9zc25wjeAERMxmcc1kfAMVU2G+fbyejIIEICGdpGiuLZ05GDPdes9uoXVGDWm1UAvfIoSZImQoqzntuMPYIsOMds7c3Yxi1OFSAIYPgwbUW1aHYeEDkO780Y0FJJ983SkKhS45LhSC/nx7lRG+Pns6RWFMEAFNVjeBX0WLouQNGDtJIIQgLwnhKp/sG05xX9PzA8PZGDFDZ+xPvpAe44XMXUUuDDUzQCJqvNahtkOSb5P4/F3mdDjmfx4h/KZBvrJF7PxQxsYcrqtGJu6bVAqEvFoTGnaYGe+2/u18qMcl3kJ9myjuCgqGvyGAKzltzBFAgMBAAECggEAeHoZSIZBo3yfy9PD5dBhJghDA+GcIt1mLTE4SMvXevEKWMhlq9IDUajwl3Y7qxF7gdIzKwEHlm5cnhq2WesKP2ZaiYaGTX6FBn/xAPghBnx8NlDpux1TThj6mZZIFsOqS4o4EcVySx7vaSs+fb4AzTQRDmlrIkNxNsUTnIa+Zzgl3OB+LHY5ONlqXXyvq8HHHfuSO+eelvk/G7Ph3NxkN7ig8j9n1Z6NGBXhN1TTchF62VnjtYD7+YUi+5HzUVu2BlVAiNS6NHsUK1OdX9+l6vfqMDXzG3NP9Fb2bQcTUe175q6rG+jNj1R3XWYU2WgTRtbu7KiCTKEDxuzE8ClBZQKBgQDhC1zWGd9X4b9NmDD1PnJlGTOPo3NuDnl5LzsYy04TprgWgZ2vANQ8smHdir8YZ1a8q4NE5A4WlOO+Iq0WHnQh+6RxwIPln18lFuJsxLbuaSeCaEyuuQzRJC10p/Obwj8QumpdmIymx+r+NsOynMcFd50rMXHR2l6/Ejoeh4Ek3wKBgQDUDzeKdIkoS9E/f2YHdpN04zwazEvs7zzBEZTBh6zTgRvllPwU9oPlCaP+O+j20iMixVn0K5AwkSI0oKsvmMMgFdQTtjUnq6ZL3i56nturYvemqfHXBnrlJJFqoj0RVA1oK4HZtmOerm+CGzXdUwP2LJgXkaW2z8ErbEP2A3aLWwKBgB1SWK9Awbv2mvFmQgLCNy97AB/YASM22Zbzk3SgkLS4xUWRb9Use3rJFz1BZbKtTDza8Aia2kcQ8TeF1x2JrEH9Qqjbl5PklwYDHE9lthwMMWypYExnqgbZ+t43P+NshMEOOOunA7R7Wd/zjL4J9Vh+BElrcNQR6sRkgJQXvbjrAoGBAMIWl2Aa3zpJgmAaq3ijN70hCe8BE3DtWm7fN4aWNkFzBuRvIvXQ1tE3rKhRjxzxIgV+GaHYiWVaQLNoZqXurJDeSbgs6aPAgDkqRuImKapIr6AUJWwH202E1a+ChGAqzQaN45AvVihzQU4Ut401oQecsyvHO6QBrKygr+coLkTHAoGAVfvMsgc/i6IHSerW4HjyxDHT0k9WpQJFCm5J79BYacRZhfGWmL2OGYx8uCawiV//f269SUmtAw1lKrz+eB9rTgtStmnf6rvfstNmflXn8iKgpqooR3JL8hfrdyOemyVLjlZAmngEjIIcswkhZmEPbAe7NR13NFXI0Ztacq5TxJE=";
	// 「沙箱环境」支付宝公钥
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnqAVxBWdpXeWheEzILKdVs+o/Bq5Lcmjy6MhMHLqBjGlIjmjks/P+EV4Q1eg8/0a1KdRCRxhWDkifuq/S3fn98mZYm/I63g/W30bAAEicboPbl6jdiQj7wIBZPT6lgSt9rHzgXRguOURDA46sOYqpZH2U05xAMkYKGKsTu7zkGt/RkgeXMJkDlG7qAc9evt2zE6cPX5KsVbxwhEKfwdfBUAE2gIo+IAlkFh6py/vSxsBXR4ZbR1oSErKip2RmMROE98+iXTpNLEZkQ04MuFhTCzQj1tsXTUe0EDovD+5WFb3LpG7Qe9hhzu2I2bvGnVUr6CVPIFukpOYUnPqc0PU3QIDAQAB";
	// 「沙箱环境」服务器异步通知页面路径。这里小傅哥用了 natapp.cn 内网穿透工具
	public static String notify_url = "http://6vkhpf.natappfree.cc/api/v1/alipay/alipay_notify_url";
	// 「沙箱环境」页面跳转同步通知页面路径 需http://格式的完整路径，必须外网可以正常访问，才会同步跳转
	public static String return_url = "http://blog.imwj.club/";
	// 「沙箱环境」
	public static String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
	// 签名方式
	public static String sign_type = "RSA2";
	// 字符编码格式
	public static String charset = "utf-8";

	@Test
	public void test_AliPay() throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
				app_id,
				merchant_private_key,
				"json",
				charset,
				alipay_public_key,
				sign_type);

		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
		request.setNotifyUrl(notify_url);
		request.setReturnUrl(return_url);

		JSONObject bizContent = new JSONObject();
		bizContent.put("out_trade_no", "d2323233333332332313361X02");  // 我们自己生成的订单编号
		bizContent.put("total_amount", "1000000"); // 订单的总金额
		bizContent.put("subject", "给莹莹转账1000000");   // 支付的名称
		bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
		request.setBizContent(bizContent.toString());

		String form = alipayClient.pageExecute(request).getBody();
		log.info("测试结果：{}", form);
	}
}
