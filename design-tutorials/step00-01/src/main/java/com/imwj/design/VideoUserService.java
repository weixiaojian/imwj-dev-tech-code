package com.imwj.design;

/**
 * 视频服务类
 * @author wj
 * @create 2023-05-19 15:26
 */
public class VideoUserService {

    public void serverGrade(String userType){
        if("VIP用户".equals(userType)){
            System.out.println("VIP用户，视频1080P蓝光");
        }else if("普通用户".equals(userType)){
            System.out.println("普通用户，视频720P高清");
        }else if("访客用户".equals(userType)){
            System.out.println("访客用户，视频480P标请");
        }
    }

}
