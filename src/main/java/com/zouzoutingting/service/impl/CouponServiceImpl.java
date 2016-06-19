package com.zouzoutingting.service.impl;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.Coupon;
import com.zouzoutingting.service.ICouponService;
import com.zouzoutingting.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangyong on 16/6/19.
 */
@Service("couponService")
public class CouponServiceImpl implements ICouponService{
    @Autowired
    private IDao<Coupon> couponDao = null;

    @Override
    public Coupon getCouponByCode(String code) {
        Coupon coupon = null;
        String condition = "code = '" + code+"'";
        Page page = new Page();
        page.setCondition(condition);
        page.setPageNo(1);
        page.setPageSize(1);
        List<Coupon> list = couponDao.page(page);
        if(list!=null && list.size()>0){
            coupon = list.get(0);
        }
        return coupon;
    }
}
