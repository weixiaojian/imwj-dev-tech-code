package com.imwj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@SpringBootApplication
public class ImwjDevTechAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImwjDevTechAppApplication.class, args);
	}

	@RequestMapping("/alipay/alipay_notify_url")
	public String alipay_notify_url(){
		log.info("收到请求...");
		return "success";
	}
}
