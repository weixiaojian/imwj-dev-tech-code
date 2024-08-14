package com.imwj.middleware.db.router;

/**
 * @author wj
 * @create 2024-08-08 10:45
 * @description
 */
public class DBRouterBase {

    private String tbIdx;

    public String getTbIdx() {
        return DBContextHolder.getTBKey();
    }

}
