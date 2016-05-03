package com.zouzoutingting.dao.impl;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.Spot;
import com.zouzoutingting.utils.Page;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangyong on 16/4/8.
 * 景点讲解数据层
 */
@Transactional
@Repository(value="spotDao")
public class SpotDaoImpl implements IDao<Spot> {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Spot load(long id) {
        return (Spot)sessionFactory.getCurrentSession().get(Spot.class, id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Spot> page(Page page) {
        Session session = sessionFactory.getCurrentSession();

        StringBuffer sb = new StringBuffer();
        sb.append("select * from t_spot");

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
        query.addEntity(Spot.class);
        return query.list();
    }

    @Override
    public int count(String condition) {
        Session session = sessionFactory.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("select count(*) from t_spot ");
        if(condition != null && !condition.equalsIgnoreCase("")) {
            sb.append(" where ");
            sb.append(condition);
        }
        SQLQuery query = session.createSQLQuery(sb.toString());
        return ((Number)query.uniqueResult()).intValue();
    }

    @Override
    public void save(Spot spot) {
        sessionFactory.getCurrentSession().save(spot);
    }

    @Override
    public void update(Spot spot) {
        sessionFactory.getCurrentSession().update(spot);
    }

    @Override
    public void delete(Spot spot) {
        sessionFactory.getCurrentSession().delete(spot);
    }
}
