package com.imwj.big.market.infrastructure.persistent.dao;

import com.imwj.big.market.infrastructure.persistent.po.RaffleActivityCount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wj
 * @create  2024-08-28 15:08
 * @description 
*/
@Mapper
public interface IRaffleActivityCountDao {

    RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);

}
