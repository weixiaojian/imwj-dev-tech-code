package com.imwj.middleware;

import com.imwj.middleware.db.router.annotation.DBRouter;

/**
 * @author wj
 * @create 2024-08-07 17:23
 * @description
 */

public interface IUserDao {

    @DBRouter(key = "userId")
    void insertUser(String req);

}
