package com.imwj.ltzf.factory;

import com.imwj.ltzf.payments.app.AppPayService;
import com.imwj.ltzf.payments.h5.H5PayService;
import com.imwj.ltzf.payments.jsp.JSPayService;
import com.imwj.ltzf.payments.jumph5.JumpH5PayService;
import com.imwj.ltzf.payments.nativepay.NativePayService;

/**
 * @author wj
 * @create 2024-04-16 17:23
 * @description 支付工厂
 */
public interface PayFactory {


    NativePayService nativePayService();

    H5PayService h5PayService();

    AppPayService appPayService();

    JSPayService jsPayService();

    JumpH5PayService jumpH5PayService();
}
