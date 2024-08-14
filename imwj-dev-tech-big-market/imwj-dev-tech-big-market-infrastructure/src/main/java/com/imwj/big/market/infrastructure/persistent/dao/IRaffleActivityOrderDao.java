package com.imwj.big.market.infrastructure.persistent.dao;

import com.imwj.big.market.infrastructure.persistent.po.RaffleActivityOrder;
import com.imwj.middleware.db.router.annotation.DBRouter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wj
 * @create 2024-08-12 17:12
 * @description
 */
@Mapper
public interface IRaffleActivityOrderDao {

    @DBRouter(key = "userId")
    void insert(RaffleActivityOrder raffleActivityOrder);

    @DBRouter(key = "userId")
    List<RaffleActivityOrder> queryRaffleActivityOrderByUserId(String userId);
}