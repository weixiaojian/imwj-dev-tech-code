package com.imwj.mybatis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author wj
 * @create 2023-08-29 17:50
 * @description 参数处理器
 */
public interface ParameterHandler {

    /**
     * 获取参数
     */
    Object getParameterObject();

    /**
     * 设置参数
     */
    void setParameters(PreparedStatement ps) throws SQLException;

}
