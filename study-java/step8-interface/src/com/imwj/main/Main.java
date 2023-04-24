package com.imwj.main;

import com.imwj.serivce.UserService;
import com.imwj.serivce.impl.UserServiceImpl;

/**
 * @author wj
 * @create 2023-04-24 16:48
 */
public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        System.out.println(userService.getUserName("1003"));
    }

}
