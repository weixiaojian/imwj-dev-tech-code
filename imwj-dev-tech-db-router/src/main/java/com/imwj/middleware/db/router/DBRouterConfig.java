package com.imwj.middleware.db.router;

/**
 * @author wj
 * @create 2024-08-07 17:21
 * @description DB路由配置
 */
public class DBRouterConfig {


    private int dbCount;  //分库数
    private int tbCount;  //分表数

    public DBRouterConfig() {
    }

    public DBRouterConfig(int dbCount, int tbCount) {
        this.dbCount = dbCount;
        this.tbCount = tbCount;
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getTbCount() {
        return tbCount;
    }

    public void setTbCount(int tbCount) {
        this.tbCount = tbCount;
    }

}
