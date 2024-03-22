package com.imwj.infratructure.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wj
 * @create 2024-03-20 17:25
 * @description 敏感词配置查询 DAO
 */
@Mapper
public interface ISensitiveWordDao {

    List<String> queryValidSensitiveWordConfig(String wordType);

}
