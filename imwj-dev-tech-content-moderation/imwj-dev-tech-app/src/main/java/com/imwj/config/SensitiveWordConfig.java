package com.imwj.config;

import com.github.houbb.sensitive.word.api.IWordAllow;
import com.github.houbb.sensitive.word.api.IWordDeny;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.imwj.infratructure.dao.ISensitiveWordDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wj
 * @create 2024-03-20 17:24
 * @description 敏感词配置 https://github.com/houbb/sensitive-word
 */
@Configuration
public class SensitiveWordConfig {

    @Bean
    public SensitiveWordBs sensitiveWordBs(IWordDeny wordDeny, IWordAllow wordAllow) {
        return SensitiveWordBs.newInstance()
                .wordDeny(wordDeny)
                .wordAllow(wordAllow)
                .ignoreCase(true)
                .ignoreWidth(true)
                .ignoreNumStyle(true)
                .ignoreChineseStyle(true)
                .ignoreEnglishStyle(true)
                .ignoreRepeat(false)
                .enableNumCheck(true)
                .enableEmailCheck(true)
                .enableUrlCheck(true)
                .enableWordCheck(true)
                .numCheckLen(1024)
                .init();
    }

    /**
     * 自定义拦截敏感词
     * @param sensitiveWordDao
     * @return
     */
    @Bean
    public IWordDeny wordDeny(ISensitiveWordDao sensitiveWordDao) {
        return new IWordDeny() {
            @Override
            public List<String> deny() {
                return sensitiveWordDao.queryValidSensitiveWordConfig("deny");
            }
        };
    }

    /**
     * 自定义放行敏感词
     * @param sensitiveWordDao
     * @return
     */
    @Bean
    public IWordAllow wordAllow(ISensitiveWordDao sensitiveWordDao) {
        return new IWordAllow() {
            @Override
            public List<String> allow() {
                return sensitiveWordDao.queryValidSensitiveWordConfig("allow");
            }
        };
    }
}
