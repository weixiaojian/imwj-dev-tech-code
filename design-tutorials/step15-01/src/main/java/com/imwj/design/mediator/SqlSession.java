package com.imwj.design.mediator;

import java.util.List;

/**
 * 义了对数据库操作的查询接口
 * @author wj
 * @create 2023-06-12 16:34
 */
public interface SqlSession {

    <T> T selectOne(String statement);

    <T> T selectOne(String statement, Object parameter);

    <T> List<T> selectList(String statement);

    <T> List<T> selectList(String statement, Object parameter);

    void close();
}
