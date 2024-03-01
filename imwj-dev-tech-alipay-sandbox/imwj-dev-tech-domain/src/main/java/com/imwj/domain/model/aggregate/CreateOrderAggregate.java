package com.imwj.domain.model.aggregate;

import com.imwj.domain.model.entity.OrderEntity;
import com.imwj.domain.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wj
 * @create 2024-02-29 18:20
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    /** 用户ID */
    private String userId;
    /** 用户ID */
    private ProductEntity productEntity;

    private OrderEntity orderEntity;

}
