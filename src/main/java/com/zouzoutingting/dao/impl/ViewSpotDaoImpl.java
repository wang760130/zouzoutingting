package com.zouzoutingting.dao.impl;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.ViewSpot;
import com.zouzoutingting.utils.Page;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangyong on 16/4/7.
 */
@Transactional
@Repository(value="viewSpotDao")
public class ViewSpotDaoImpl implements IDao<ViewSpot> {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ViewSpot load(long id) {
        return (ViewSpot)sessionFactory.getCurrentSession().get(ViewSpot.class, id);
    }

    @Override
    public List<ViewSpot> page(Page page) {
        Session session = sessionFactory.getCurrentSession();

        StringBuffer sb = new StringBuffer();
        sb.append("select * from t_viewspot");

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
        query.addEntity(ViewSpot.class);
        return query.list();
    }

    @Override
    public int count(String condition) {
        Session session = sessionFactory.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("select count(*) from t_viewspot ");
        if(condition != null && !condition.equalsIgnoreCase("")) {
            sb.append(" where ");
            sb.append(condition);
        }
        SQLQuery query = session.createSQLQuery(sb.toString());
        return ((Number)query.uniqueResult()).intValue();
    }

    @Override
    public void save(ViewSpot viewSpot) {
        sessionFactory.getCurrentSession().save(viewSpot);
    }

    @Override
    public void update(ViewSpot viewSpot) {
        sessionFactory.getCurrentSession().update(viewSpot);
    }

    @Override
    public void delete(ViewSpot viewSpot) {
        sessionFactory.getCurrentSession().delete(viewSpot);
    }
}
