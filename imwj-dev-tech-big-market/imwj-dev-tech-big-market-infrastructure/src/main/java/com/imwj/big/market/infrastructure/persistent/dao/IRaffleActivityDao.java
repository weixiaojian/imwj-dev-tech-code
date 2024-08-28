package com.imwj.big.market.infrastructure.persistent.dao;

import com.imwj.big.market.infrastructure.persistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wj
 * @create  2024-08-28 15:08
 * @description 
*/

@Mapper
public interface IRaffleActivityDao {

    RaffleActivity queryRaffleActivityByActivityId(Long activityId);

}
