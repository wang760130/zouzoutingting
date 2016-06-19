package com.zouzoutingting.service.impl;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.Order;
import com.zouzoutingting.service.IOrderService;
import com.zouzoutingting.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangyong on 16/6/19.
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService{
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
        return orderDao.load(oid);
    }

    @Override
    public Order insertOrder(Order order) {
        long oid = orderDao.save(order);
        order.setOrderid(oid);
        return order;
    }
}
