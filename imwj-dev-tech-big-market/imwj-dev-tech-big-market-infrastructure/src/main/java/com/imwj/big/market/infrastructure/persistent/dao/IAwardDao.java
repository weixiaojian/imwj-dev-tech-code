package com.imwj.big.market.infrastructure.persistent.dao;

import com.imwj.big.market.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wj
 * @create 2024-04-18 17:24
 * @description 奖品表dao
 */
@Mapper
public interface IAwardDao {

    List<Award> queryAwardList();

}
