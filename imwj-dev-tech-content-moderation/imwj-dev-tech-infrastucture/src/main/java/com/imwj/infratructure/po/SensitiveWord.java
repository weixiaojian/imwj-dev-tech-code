package com.imwj.infratructure.po;

import java.util.Date;

/**
 * @author wj
 * @create 2024-03-20 17:26
 * @description 敏感词
 */
public class SensitiveWord {

    /** 自增ID */
    private Long id;
    /** 类型；allow-允许，deny-拒绝 */
    private String wordType;
    /** 敏感词 */
    private String word;
    /** 是否有效；0无效、1有效 */
    private Integer isValid;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
