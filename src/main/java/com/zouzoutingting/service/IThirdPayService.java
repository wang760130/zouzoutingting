package com.zouzoutingting.service;

import com.zouzoutingting.model.Order;

/**
 * Created by zhangyong on 16/8/21.
 * 第三方支付服务接口
 */
public interface IThirdPayService {
    boolean isPayedBySearchThird(Order order);
}
