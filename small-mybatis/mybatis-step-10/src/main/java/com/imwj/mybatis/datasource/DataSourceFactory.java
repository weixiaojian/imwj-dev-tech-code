package com.imwj.mybatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author wj
 * @create 2023-07-26 16:56
 * @description 数据源工厂
 */
public interface DataSourceFactory {

    void setProperties(Properties props);

    DataSource getDataSource();

}
