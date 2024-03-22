package com.imwj.test;

import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author wj
 * @create 2024-03-05 17:38
 * @description
 */
@Slf4j
public class SensitiveWordTest {

    @Test
    public void test_sensitive_word() {
        log.info("是否被敏感词拦截：{}", SensitiveWordHelper.contains("测试。"));
    }

    @Test
    public void test_sensitive_word_findAll() {
        log.info("测试结果：{}", SensitiveWordHelper.findAll("五星红旗迎风飘扬，毛主席的画像屹立在天安门前。"));
    }

    @Test
    public void test_sensitive_word_replace() {
        log.info("测试结果：{}",  SensitiveWordHelper.replace(" 五星红旗迎风飘扬，毛主席的画像屹立在天安门前。"));
    }
}
