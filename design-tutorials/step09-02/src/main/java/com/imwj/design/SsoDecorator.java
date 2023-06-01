package com.imwj.design;

/**
 * 抽象类装饰角色（将原有的逻辑接口接入）
 * @author wj
 * @create 2023-06-01 17:51
 */
public abstract class SsoDecorator {

    private HandlerInterceptor handlerInterceptor;


    /**
     * 将原有的逻辑方法传入
     * @param handlerInterceptor
     */
    public SsoDecorator(HandlerInterceptor handlerInterceptor){
        this.handlerInterceptor = handlerInterceptor;
    }

    /**
     * 继承原有的逻辑方法
     * @param request
     * @param response
     * @param handler
     * @return
     */
    public boolean preHandle(String request, String response, Object handler) {
        return handlerInterceptor.preHandle(request, response, handler);
    }


}
