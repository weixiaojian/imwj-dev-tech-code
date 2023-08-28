package com.imwj.mybatis.datasource.unpooled;

import com.imwj.mybatis.datasource.DataSourceFactory;
import com.imwj.mybatis.reflection.MetaObject;
import com.imwj.mybatis.reflection.SystemMetaObject;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author wj
 * @create 2023-07-31 17:36
 * @description 无池化工厂 主要用于创建UnpooledDataSource
 */
public class UnpooledDataSourceFactory implements DataSourceFactory {


    protected DataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Properties props) {
        // 1.先加载指定对象
        MetaObject metaObject = SystemMetaObject.forObject(dataSource);
        // 2.循环xml配置值
        for (Object key : props.keySet()) {
            String propertyName = (String) key;
            // 3.两者属性一致(有对应的set方法)就进行天才
            if (metaObject.hasSetter(propertyName)) {
                String value = (String) props.get(propertyName);
                Object convertedValue = convertValue(metaObject, propertyName, value);
                metaObject.setValue(propertyName, convertedValue);
            }
        }
    }

    /**
     * 根据setter的类型,将配置文件中的值强转成相应的类型
     */
    private Object convertValue(MetaObject metaObject, String propertyName, String value) {
        Object convertedValue = value;
        Class<?> targetType = metaObject.getSetterType(propertyName);
        if (targetType == Integer.class || targetType == int.class) {
            convertedValue = Integer.valueOf(value);
        } else if (targetType == Long.class || targetType == long.class) {
            convertedValue = Long.valueOf(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            convertedValue = Boolean.valueOf(value);
        }
        return convertedValue;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
