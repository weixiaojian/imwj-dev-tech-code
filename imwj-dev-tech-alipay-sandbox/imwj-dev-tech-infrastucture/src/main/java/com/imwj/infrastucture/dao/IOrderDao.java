package com.imwj.infrastucture.dao;

import org.apache.ibatis.annotations.Mapper;
import com.imwj.infrastucture.po.PayOrder;

/**
 * @author wj
 * @create 2024-02-29 18:46
 * @description
 */
@Mapper
public interface IOrderDao {

    void insert(PayOrder order);

    PayOrder queryUnPayOrder(PayOrder order);

    void updateOrderPayInfo(PayOrder order);

    void changeOrderPaySuccess(PayOrder order);

}
