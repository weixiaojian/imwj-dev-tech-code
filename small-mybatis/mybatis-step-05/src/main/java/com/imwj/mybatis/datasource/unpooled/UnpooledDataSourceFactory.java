package com.imwj.mybatis.datasource.unpooled;

import com.imwj.mybatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author wj
 * @create 2023-07-31 17:36
 * @description 无池化工厂 主要用于创建UnpooledDataSource
 */
public class UnpooledDataSourceFactory implements DataSourceFactory {

    protected Properties props;

    @Override
    public void setProperties(Properties props) {
        this.props = props;
    }

    @Override
    public DataSource getDataSource() {
        UnpooledDataSource unpooledDataSource = new UnpooledDataSource();
        unpooledDataSource.setDriver(props.getProperty("driver"));
        unpooledDataSource.setUrl(props.getProperty("url"));
        unpooledDataSource.setUsername(props.getProperty("username"));
        unpooledDataSource.setPassword(props.getProperty("password"));
        return unpooledDataSource;
    }
}
