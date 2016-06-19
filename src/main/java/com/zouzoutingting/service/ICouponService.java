package com.zouzoutingting.service;

import com.zouzoutingting.model.Coupon;

/**
 * Created by zhangyong on 16/6/19.
 * 代金券服务层
 */
public interface ICouponService {
    public Coupon getCouponByCode(String code);
}
