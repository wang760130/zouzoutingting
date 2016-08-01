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
     * @return 成功与否
     */
    public boolean CounponPay(Order order);

    public void update(Order order);
}
