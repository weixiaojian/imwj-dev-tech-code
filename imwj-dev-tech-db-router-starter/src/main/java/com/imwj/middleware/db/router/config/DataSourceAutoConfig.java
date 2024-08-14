package com.imwj.middleware.db.router.config;

import com.imwj.middleware.db.router.DBRouterConfig;
import com.imwj.middleware.db.router.dynamic.DynamicDataSource;
import com.imwj.middleware.db.router.dynamic.DynamicMybatisPlugin;
import com.imwj.middleware.db.router.utils.PropertyUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wj
 * @create 2024-08-07 17:20
 * @description 数据源自动配置类
 */
@Configuration
@ComponentScan(value = "com.imwj")
public class DataSourceAutoConfig implements EnvironmentAware {

    private Map<String, Map<String, Object>> dataSourceMap = new HashMap<>();

    private int dbCount;  //分库数
    private int tbCount;  //分表数

    @Bean
    public DBRouterConfig dbRouterConfig() {
        return new DBRouterConfig(dbCount, tbCount);
    }

    @Bean
    public Interceptor plugin() {
        return new DynamicMybatisPlugin();
    }

    @Bean
    public DataSource dataSource() {
        // 创建数据源
        HashMap<Object, Object> targetDataSources = new HashMap<>();
        for(String dbInfo : dataSourceMap.keySet()){
            Map<String, Object> objectMap = dataSourceMap.get(dbInfo);
            targetDataSources.put(dbInfo, new DriverManagerDataSource(objectMap.get("url").toString(),
                    objectMap.get("username").toString(), objectMap.get("password").toString()));
        }
        // 设置数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    /**
     * 读取并设置环境配置
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        String prefix = "router.jdbc.datasource.";

        dbCount = Integer.valueOf(environment.getProperty(prefix + "dbCount"));
        tbCount = Integer.valueOf(environment.getProperty(prefix + "tbCount"));

        String dataSources = environment.getProperty(prefix + "list");
        for (String dbInfo : dataSources.split(",")) {
            Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + dbInfo, Map.class);
            dataSourceMap.put(dbInfo, dataSourceProps);
        }
    }
}
