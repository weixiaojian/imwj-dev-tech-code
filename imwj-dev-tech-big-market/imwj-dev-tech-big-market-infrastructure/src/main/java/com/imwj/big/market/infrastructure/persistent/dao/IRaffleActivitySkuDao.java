package com.imwj.big.market.infrastructure.persistent.dao;

import com.imwj.big.market.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wj
 * @create 2024-08-28 15:10
 * @description
 */
@Mapper
public interface IRaffleActivitySkuDao {

    RaffleActivitySku queryActivitySku(Long sku);

}
