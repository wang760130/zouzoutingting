package com.zouzoutingting.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.components.dao.DaoImpl;
import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.model.Order;

/**
 * Created by zhangyong on 16/6/19.
 */
@Transactional
@Repository(value="orderDao")
public class OrderDaoImpl extends DaoImpl<Order> implements IDao<Order> {}
