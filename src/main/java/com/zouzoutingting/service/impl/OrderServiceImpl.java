package com.zouzoutingting.service.impl;

import com.alibaba.fastjson.JSON;
import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.enums.OrderStateEnum;
import com.zouzoutingting.model.Order;
import com.zouzoutingting.service.IOrderService;
import com.zouzoutingting.utils.Page;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 16/6/19.
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService{
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    private IDao<Order> orderDao;

    @Override
    public Order getOrderByUidAndVid(long uid, long vid) {
        Order order = null;
        String condition = "uid = " + uid + " and vid= "+vid;
        Page page = new Page();
        page.setCondition(condition);
        page.setPageNo(1);
        page.setPageSize(1);
        List<Order> list = orderDao.page(page);
        if(list!=null && list.size()>0){
            order = list.get(0);
        }
        return order;
    }

    @Override
    public Order getOrderByID(long oid) {
        Order order = null;
        if(oid>0){
            order = orderDao.load(oid);
        }
        return order;
    }

    @Override
    public Order insertOrder(Order order) {
        long oid = orderDao.save(order);
        order.setOrderid(oid);
        return order;
    }

    @Override
    public boolean CounponPay(Order order, long couponid) {
        boolean retResult = false;
        if(order!=null && order.getState() == OrderStateEnum.Jinx.getState()){
            order.setState(OrderStateEnum.Finish.getState());
            Map<String,String> map = new HashMap<String, String>();
            map.put("paytype","coupon");
            map.put("trade_no",""+couponid);
            order.setComment(JSON.toJSONString(map));
            order.setPayTime(new Date());
            order.setUpdateTime(new Date());
            orderDao.update(order);
            logger.info("couon pay update order jinxing-->finish oid:"+order.getOrderid());
            retResult = true;
        }else{
            if(order==null){
                logger.error("coupon pay error order is null");
            }else {
                logger.error("coupon pay error order state is wrong oid:"+order.getOrderid()+", state:"+ order.getState
                        ());
            }
        }
        return retResult;
    }

    @Override
    public void update(Order order) {
       orderDao.update(order);
    }
}
