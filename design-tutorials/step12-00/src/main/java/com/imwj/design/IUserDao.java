package com.imwj.design;

import com.imwj.design.agent.Select;

/**
 * 模拟mybatis的dao接口
 * @author wj
 * @create 2023-06-06 15:38
 */
public interface IUserDao {

    @Select("select userName from user where id = #{uId}")
    String queryUserInfo(String uId);

}
