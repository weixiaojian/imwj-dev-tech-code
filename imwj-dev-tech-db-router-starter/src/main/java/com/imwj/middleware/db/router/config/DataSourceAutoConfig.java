package com.imwj.middleware.db.router.config;

import com.imwj.middleware.db.router.DBRouterConfig;
import com.imwj.middleware.db.router.DBRouterJoinPoint;
import com.imwj.middleware.db.router.dynamic.DynamicDataSource;
import com.imwj.middleware.db.router.dynamic.DynamicMybatisPlugin;
import com.imwj.middleware.db.router.utils.PropertyUtil;
import com.imwj.middleware.db.router.utils.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author wj
 * @create 2024-08-07 17:20
 * @description 数据源自动配置类
 */
@Configuration
@ComponentScan(value = "com.imwj")
public class DataSourceAutoConfig implements EnvironmentAware {

    /**
     * 分库全局属性
     */
    private static final String TAG_GLOBAL = "global";

    /**
     * 连接池属性
     */
    private static final String TAG_POOL = "pool";


    private Map<String, Map<String, Object>> dataSourceMap = new HashMap<>();

    private int dbCount;  //分库数
    private int tbCount;  //分表数

    /**
     * 路由字段
     */
    private String routerKey;

    /**
     * 默认数据源配置
     */
    private Map<String, Object> defaultDataSourceConfig;


    @Bean
    public DBRouterConfig dbRouterConfig() {
        return new DBRouterConfig(dbCount, tbCount);
    }

    @Bean
    public Interceptor plugin() {
        return new DynamicMybatisPlugin();
    }

    @Bean
    public DataSource createDataSource() {
        // 创建数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        for (String dbInfo : dataSourceMap.keySet()) {
            Map<String, Object> objMap = dataSourceMap.get(dbInfo);
            // 根据objMap创建DataSourceProperties,遍历objMap根据属性反射创建DataSourceProperties
            DataSource ds = createDataSource(objMap);
            targetDataSources.put(dbInfo, ds);

        }

        // 设置数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // db0为默认数据源
        dynamicDataSource.setDefaultTargetDataSource(createDataSource(defaultDataSourceConfig));

        return dynamicDataSource;
    }

    private DataSource createDataSource(Map<String, Object> attributes) {
        try {
            DataSourceProperties dataSourceProperties = new DataSourceProperties();
            dataSourceProperties.setUrl(attributes.get("url").toString());
            dataSourceProperties.setUsername(attributes.get("username").toString());
            dataSourceProperties.setPassword(attributes.get("password").toString());

            String driverClassName = attributes.get("driver-class-name") == null ? "com.zaxxer.hikari.HikariDataSource" : attributes.get("driver-class-name").toString();
            dataSourceProperties.setDriverClassName(driverClassName);

            String typeClassName = attributes.get("type-class-name") == null ? "com.zaxxer.hikari.HikariDataSource" : attributes.get("type-class-name").toString();
            DataSource ds = dataSourceProperties.initializeDataSourceBuilder().type((Class<DataSource>) Class.forName(typeClassName)).build();

            MetaObject dsMeta = SystemMetaObject.forObject(ds);
            Map<String, Object> poolProps = (Map<String, Object>) (attributes.containsKey(TAG_POOL) ? attributes.get(TAG_POOL) : Collections.EMPTY_MAP);
            for (Map.Entry<String, Object> entry : poolProps.entrySet()) {
                // 中划线转驼峰
                String key = StringUtils.middleScoreToCamelCase(entry.getKey());
                if (dsMeta.hasSetter(key)) {
                    dsMeta.setValue(key, entry.getValue());
                }
            }
            return ds;
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("can not find datasource type class by class name", e);
        }
    }

    /**
     * 读取并设置环境配置
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        String prefix = "router.jdbc.datasource.";

        dbCount = Integer.parseInt(Objects.requireNonNull(environment.getProperty(prefix + "dbCount")));
        tbCount = Integer.parseInt(Objects.requireNonNull(environment.getProperty(prefix + "tbCount")));
        routerKey = environment.getProperty(prefix + "routerKey");
        // 分库分表数据源
        String dataSources = environment.getProperty(prefix + "list");
        Map<String, Object> globalInfo = getGlobalProps(environment, prefix + TAG_GLOBAL);
        for (String dbInfo : dataSources.split(",")) {
            final String dbPrefix = prefix + dbInfo;
            Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, dbPrefix, Map.class);
            injectGlobal(dataSourceProps, globalInfo);
            dataSourceMap.put(dbInfo, dataSourceProps);
        }

        // 默认数据源
        String defaultData = environment.getProperty(prefix + "default");
        defaultDataSourceConfig = PropertyUtil.handle(environment, prefix + defaultData, Map.class);
        injectGlobal(defaultDataSourceConfig, globalInfo);
    }

    private void injectGlobal(Map<String, Object> origin, Map<String, Object> global) {
        for (String key : global.keySet()) {
            if (!origin.containsKey(key)) {
                origin.put(key, global.get(key));
            } else if (origin.get(key) instanceof Map) {
                injectGlobal((Map<String, Object>) origin.get(key), (Map<String, Object>) global.get(key));
            }
        }
    }

    private Map<String, Object> getGlobalProps(Environment environment, String key) {
        try {
            return PropertyUtil.handle(environment, key, Map.class);
        } catch (Exception e) {
            return Collections.EMPTY_MAP;
        }
    }
}
