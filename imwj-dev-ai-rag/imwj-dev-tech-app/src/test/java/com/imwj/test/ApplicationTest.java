package com.imwj.test;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.IOException;

/**
 * @author wj
 * @create 2025-05-28 17:06
 * @description
 */
public class ApplicationTest {

    private static final String API_URL = "http://192.168.208.130:11434/api/generate";

    public static void main(String[] args) {
        // 构建请求参数
        JSONObject param = new JSONObject();
        param.put("model", "deepseek-r1:1.5b");
        param.put("prompt", "你好");
        param.put("stream", false);

        // 发送POST请求
        HttpResponse response = HttpRequest.post(API_URL)
                .header("Content-Type", "application/json")
                .body(param.toString()) // 直接传递JSON字符串
                .execute();

        // 处理响应
        if (response.isOk()) {
            String result = response.body();
            System.out.println("请求成功，响应结果：");
            JSONObject resultJson = JSONUtil.parseObj(result);
            System.out.println(resultJson.getStr("response")); // 输出具体回答内容
        } else {
            System.out.println("请求失败，状态码：" + response.getStatus());
            System.out.println("错误信息：" + response.body());
        }
    }

}
