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

    /**
     * 券支付时状态更新
     * @param order order实体
     * @param counponId 券id
     * @return 成功与否
     */
    public boolean CounponPay(Order order, long counponId);

    public void update(Order order);
}
