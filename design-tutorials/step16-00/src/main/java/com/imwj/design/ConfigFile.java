package com.imwj.design;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 配置文件类
 * @author wj
 * @create 2023-06-13 11:27
 */
@Data
@AllArgsConstructor
public class ConfigFile {

    private String versionNo; // 版本号
    private String content;   // 内容
    private Date dateTime;    // 时间
    private String operator;  // 操作人
}
