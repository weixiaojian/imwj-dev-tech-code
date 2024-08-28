package com.imwj.middleware.db.router.dynamic;

import com.imwj.middleware.db.router.DBContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author wj
 * @create 2024-08-08 10:43
 * @description
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Value("${router.jdbc.datasource.default}")
    private String defaultDataSource;

    @Override
    protected Object determineCurrentLookupKey() {
        if (null == DBContextHolder.getDBKey()) {
            return defaultDataSource;
        } else {
            return "db" + DBContextHolder.getDBKey();
        }
    }

}
