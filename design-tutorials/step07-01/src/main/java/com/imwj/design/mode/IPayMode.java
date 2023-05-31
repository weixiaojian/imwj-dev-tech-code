package com.imwj.design.mode;

/**
 * @author wj
 * @create 2023-05-31 15:55
 */
public interface IPayMode {


    /**
     * 支付是否安全
     * @param uId
     * @return
     */
    boolean security(String uId);

}
