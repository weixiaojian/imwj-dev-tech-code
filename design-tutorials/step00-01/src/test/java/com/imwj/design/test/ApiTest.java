package com.imwj.design.test;

import com.imwj.design.service.IVideoUserService;
import com.imwj.design.service.impl.VipUserServiceImpl;

/**
 * @author wj
 * @create 2023-05-22 11:10
 */
public class ApiTest {

    public static void main(String[] args) {
        IVideoUserService service = new VipUserServiceImpl();
        service.advertisement();
        service.definition();
    }

}
