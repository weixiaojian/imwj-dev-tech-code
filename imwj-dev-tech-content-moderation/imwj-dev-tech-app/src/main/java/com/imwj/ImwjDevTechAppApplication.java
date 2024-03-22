package com.imwj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wj
 * @create 2024-03-05 17:02
 * @description
 */
@Slf4j
@RestController
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
