package com.imwj.test;

import com.baidu.aip.contentcensor.AipContentCensor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wj
 * @create 2024-03-05 17:03
 * @description
 */
@Slf4j
@SpringBootTest
public class BaiduContextTest {

    //设置APPID/AK/SK
    public static final String APP_ID = "55259444";
    public static final String API_KEY = "IHB2mQeuex4VHC3Wx4h4udj6";
    public static final String SECRET_KEY = "WUmeGhytuTko4mlt17QfNjx7vqC285zz";
    private AipContentCensor client;

    @Before
    public void init() {
        client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

    @Test
    public void test_textCensorUserDefined() throws Exception {
        init();
        for (int i = 0; i < 1; i++) {
            JSONObject jsonObject = client.textCensorUserDefined("五星红旗迎风飘扬，毛主席的画像屹立在天安门前。");
            if (!jsonObject.isNull("error_code")) {
                log.info("测试结果：{}", jsonObject.get("error_code"));
            } else {
                log.info("测试结果：{}", jsonObject.toString());
            }
        }
    }
}
