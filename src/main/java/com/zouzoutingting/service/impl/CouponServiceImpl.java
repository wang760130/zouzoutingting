package com.zouzoutingting.service.impl;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.enums.CouponStateEnum;
import com.zouzoutingting.model.Coupon;
import com.zouzoutingting.service.ICouponService;
import com.zouzoutingting.utils.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangyong on 16/6/19.
 */
@Service("couponService")
public class CouponServiceImpl implements ICouponService{
    private static final Logger logger = Logger.getLogger(CouponServiceImpl.class);
    @Override
    public boolean useCounpon(Coupon coupon) {
        boolean retResult = false;
        if(coupon!=null && coupon.getState()== CouponStateEnum.Available.getState()){
            coupon.setState(CouponStateEnum.Used.getState());
            couponDao.update(coupon);
            logger.info("counp "+coupon.getCouponid()+", "+coupon.getCode()+" avilable--> used");
            retResult = true;
        }

        return retResult;
    }

    @Override
    public boolean rollBackCouponPay(Coupon coupon) {
        boolean retResult = false;
        if(coupon!=null && coupon.getState()== CouponStateEnum.Used.getState()){
            coupon.setState(CouponStateEnum.Available.getState());
            couponDao.update(coupon);
            logger.info("counp "+coupon.getCouponid()+", "+coupon.getCode()+" used-->avilable");
            retResult = true;
        }
        return retResult;
    }

    @Override
    public long save(Coupon coupon) {
        return couponDao.save(coupon);
    }

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
