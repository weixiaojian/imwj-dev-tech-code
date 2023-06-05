package com.imwj.design.controller;

import com.imwj.design.domain.UserInfo;
import lombok.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wj
 * @create 2023-06-05 16:20
 */
@RestController
public class HelloWorldController {


    // @RequestMapping(path = "/api/queryUserInfo", method = RequestMethod.GET)
    public UserInfo queryUserInfo(@RequestParam String userId) {
        return new UserInfo("虫虫:" + userId, 19, "天津市南开区旮旯胡同100号");
    }

}
