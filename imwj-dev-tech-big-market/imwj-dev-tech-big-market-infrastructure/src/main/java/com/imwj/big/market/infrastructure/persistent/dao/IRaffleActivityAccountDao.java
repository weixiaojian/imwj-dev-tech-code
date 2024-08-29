package com.imwj.big.market.infrastructure.persistent.dao;


import com.imwj.big.market.infrastructure.persistent.po.RaffleActivityAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wj
 * @create 2024-08-12 17:12
 * @description
 */
@Mapper
public interface IRaffleActivityAccountDao {

    /**
     * 插入数据
     * @param raffleActivityAccount
     */
    void insert(RaffleActivityAccount raffleActivityAccount);

    /**
     * 更新活动账户信息
     * @param raffleActivityAccount
     * @return
     */
    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);

}
