package com.imwj.mybatis.type;

import cn.hutool.db.meta.JdbcType;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wj
 * @create 2023-08-29 17:36
 * @description Long类型处理器
 */
public class DateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    protected void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        ps.setDate(i, parameter);
    }


    @Override
    protected Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getDate(columnName);
    }
}
