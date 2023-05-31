package com.imwj.design.mode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wj
 * @create 2023-05-31 16:25
 */
public class PayFaceMode implements IPayMode{
    protected Logger logger = LoggerFactory.getLogger(IPayMode.class);


    @Override
    public boolean security(String uId) {
        logger.info("人脸支付，风控校验脸部识别");
        return true;
    }

}
