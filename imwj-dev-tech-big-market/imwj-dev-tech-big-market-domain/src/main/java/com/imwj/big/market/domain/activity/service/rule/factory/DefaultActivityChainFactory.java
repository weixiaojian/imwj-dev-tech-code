package com.imwj.big.market.domain.activity.service.rule.factory;

import com.imwj.big.market.domain.activity.service.rule.IActionChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wj
 * @create 2024-08-28 17:22
 * @description 默认的抽奖活动链工厂类
 */
@Service
public class DefaultActivityChainFactory {

    private final IActionChain actionChain;

    /**
     * 1.通过构造函数注入
     * 2.Spring可以自动注入IActionChain 接口实现类到map对象中，key就是bean的名称
     * 3.活动下单的动作责任链是固定的，所以直接在构造函数中组转即可
     * @param actionChainGroup
     */
    public DefaultActivityChainFactory(Map<String, IActionChain> actionChainGroup) {
        actionChain = actionChainGroup.get(ActionModel.activity_base_action.code);
        actionChain.appendNext(actionChainGroup.get(ActionModel.activity_sku_stock_action.getCode()));
    }


    /**
     * 获取当前责任链
     * @return
     */
    public IActionChain openActionChain(){
        return this.actionChain;
    }

    @Getter
    @AllArgsConstructor
    public enum ActionModel {

        activity_base_action("activity_base_action", "活动的库存、时间校验"),
        activity_sku_stock_action("activity_sku_stock_action", "活动sku库存"),
        ;

        private final String code;
        private final String info;

    }

}
