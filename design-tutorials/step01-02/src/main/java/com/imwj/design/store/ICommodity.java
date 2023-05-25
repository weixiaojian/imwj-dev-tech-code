package com.imwj.design.store;

import java.util.Map;

/**
 * 发放接口
 * @author wj
 * @create 2023-05-24 17:52
 */
public interface ICommodity {

    void sendCommodity(String uId, String commodityId, String bizId, Map<String, String> extMap) throws Exception;

}
