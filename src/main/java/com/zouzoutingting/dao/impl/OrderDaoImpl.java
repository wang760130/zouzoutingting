package com.zouzoutingting.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.Order;
import com.zouzoutingting.utils.Page;

/**
 * Created by zhangyong on 16/6/19.
 */
@Transactional
@Repository(value="orderDao")
public class OrderDaoImpl implements IDao<Order> {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order load(long id) {
        return (Order) sessionFactory.getCurrentSession().get(Order.class, id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Order> page(Page page) {
        Session session = sessionFactory.getCurrentSession();

        StringBuffer sb = new StringBuffer();
        sb.append("select * from t_order");

        String condition = page.getCondition();
        if(condition != null && !condition.equalsIgnoreCase("")) {
            sb.append(" where ");
            sb.append(condition);
        }

        if (page.getOrderName() != null && !page.getOrderName().equalsIgnoreCase("")) {
            sb.append(" order by ");
            sb.append(page.getOrderName());
        }

        int offset = page.getPageSize() * (page.getPageNo() - 1);
        sb.append(" limit ");
        sb.append(offset);
        sb.append(",");
        sb.append(page.getPageSize());

        SQLQuery query = session.createSQLQuery(sb.toString());
        query.addEntity(Order.class);
        return query.list();
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<Order> list(String condition, String orderName) {
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_order");
		
		if(condition != null && !condition.equalsIgnoreCase("")) {
			sb.append(" where ");
			sb.append(condition);
		}
		
		if (orderName != null && !orderName.equalsIgnoreCase("")) {
			sb.append(" order by ");
			sb.append(orderName);
		}
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addEntity(Order.class);
		return query.list();
	}
    
    @Override
    public int count(String condition) {
        Session session = sessionFactory.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("select count(*) from t_order ");
        if(condition != null && !condition.equalsIgnoreCase("")) {
            sb.append(" where ");
            sb.append(condition);
        }
        SQLQuery query = session.createSQLQuery(sb.toString());
        return ((Number)query.uniqueResult()).intValue();
    }

    @Override
    public long save(Order order) {
        return (Long)sessionFactory.getCurrentSession().save(order);
    }

    @Override
    public void update(Order order) {
        sessionFactory.getCurrentSession().update(order);
    }

    @Override
    public void delete(Order order) {
        sessionFactory.getCurrentSession().delete(order);
    }
}
