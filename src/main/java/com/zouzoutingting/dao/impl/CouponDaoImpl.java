package com.zouzoutingting.dao.impl;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.City;
import com.zouzoutingting.model.Coupon;
import com.zouzoutingting.utils.Page;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangyong on 16/6/19.
 */
@Transactional
@Repository(value="couponDao")
public class CouponDaoImpl implements IDao<Coupon> {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Coupon load(long id) {
        return (Coupon) sessionFactory.getCurrentSession().get(Coupon.class, id);
    }

    @Override
    public List<Coupon> page(Page page) {
        Session session = sessionFactory.getCurrentSession();

        StringBuffer sb = new StringBuffer();
        sb.append("select * from t_coupon");

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
        query.addEntity(City.class);
        return query.list();
    }

    @Override
    public int count(String condition) {
        Session session = sessionFactory.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("select count(*) from t_coupon ");
        if(condition != null && !condition.equalsIgnoreCase("")) {
            sb.append(" where ");
            sb.append(condition);
        }
        SQLQuery query = session.createSQLQuery(sb.toString());
        return ((Number)query.uniqueResult()).intValue();
    }

    @Override
    public long save(Coupon coupon) {
        return (Long)sessionFactory.getCurrentSession().save(coupon);
    }

    @Override
    public void update(Coupon coupon) {
        sessionFactory.getCurrentSession().update(coupon);
    }

    @Override
    public void delete(Coupon coupon) {
        sessionFactory.getCurrentSession().delete(coupon);
    }
}
