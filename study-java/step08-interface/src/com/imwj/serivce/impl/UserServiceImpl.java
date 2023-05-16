package com.imwj.serivce.impl;

import com.imwj.serivce.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wj
 * @create 2023-04-24 16:44
 */
public class UserServiceImpl implements UserService {
    Map<String, String> map = new HashMap<>();
    {
        map.put("1001", "张三");
        map.put("1002", "李四");
        map.put("1003", "王五");
    }

    @Override
    public String getUserName(String id) {
        if(map.containsKey(id)){
            return map.get(id);
        }else{
            return null;
        }
    }
}
