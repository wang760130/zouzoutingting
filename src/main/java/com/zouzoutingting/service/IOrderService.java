package com.zouzoutingting.service;

import com.zouzoutingting.model.Order;

/**
 * Created by zhangyong on 16/6/19.
 * 订单相关逻辑接口
 */
public interface IOrderService {
    public Order getOrderByUidAndVid(long uid, long vid);

    public Order getOrderByID(long oid);

    public Order insertOrder(Order order);
}
