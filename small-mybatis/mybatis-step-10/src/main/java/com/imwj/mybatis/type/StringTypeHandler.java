package com.imwj.mybatis.type;

import cn.hutool.db.meta.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author wj
 * @create 2023-08-29 17:36
 * @description Long类型处理器
 */
public class StringTypeHandler extends BaseTypeHandler<String> {
    @Override
    protected void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }
}
