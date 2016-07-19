package com.zouzoutingting.service;

import com.zouzoutingting.model.Coupon;

/**
 * Created by zhangyong on 16/6/19.
 * 代金券服务层
 */
public interface ICouponService {
    public Coupon getCouponByCode(String code);

    /**
     * 券支付 使用该券
     * @param coupon 券实体
     * @return
     */
    public boolean useCounpon(Coupon coupon);

    /**
     * 回滚券支付
     * @param coupon 券支付后实体
     * @return
     */
    public boolean rollBackCouponPay(Coupon coupon);
}
