package com.imwj.domain.model.entity;

import com.imwj.domain.model.valobj.OrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wj
 * @create 2024-02-29 18:21
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderEntity {

    /** 用户ID */
    private String userId;
    /** 订单ID */
    private String orderId;
    /** 支付地址；创建支付后，获得支付信息；*/
    private String payUrl;
    /** 订单状态；0-创建完成、1-等待支付、2-支付成功、3-交易完成、4-订单关单 */
    private OrderStatusVO orderStatus;

    @Override
    public String toString() {
        return "PayOrderEntity{" +
                "userId='" + userId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", payUrl='" + payUrl + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }

}
