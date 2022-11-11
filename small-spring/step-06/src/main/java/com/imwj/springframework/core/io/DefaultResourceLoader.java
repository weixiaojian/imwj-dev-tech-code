package com.imwj.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.net.URL;

/**
 * 默认资源加载器
 * @author wj
 * @create 2022-11-01 17:26
 */
public class DefaultResourceLoader implements ResourceLoader{


    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            // class读取
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }else {
            try {
                // url读取
                URL url = new URL(location);
                return new UrlResource(url);
            }catch (Exception e){
                // 文件读取
                return new FileSystemResource(location);
            }
        }
    }
}
