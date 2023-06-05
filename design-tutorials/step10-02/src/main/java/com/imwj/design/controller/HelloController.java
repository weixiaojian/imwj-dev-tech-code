package com.imwj.design.controller;

import com.imwj.design.domain.UserInfo;
import com.imwj.design.door.annotation.DoDoor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wj
 * @create 2023-06-05 16:57
 */
@RestController
public class HelloController {

    @DoDoor(key = "userId", returnJson = "{\"code\":\"1111\",\"info\":\"非白名单可访问用户拦截！\"}")
    @RequestMapping(path = "/api/queryUserInfo", method = RequestMethod.GET)
    public UserInfo queryUserInfo(@RequestParam String userId) {
        return new UserInfo("团团:" + userId, 19, "天津市南开区旮旯胡同100号");
    }


}
