package com.zouzoutingting.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.City;
import com.zouzoutingting.model.Feedback;
import com.zouzoutingting.utils.Page;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月24日
 */
@Transactional
@Repository(value="feedbackDao")
public class FeedbackDaoImpl implements IDao<Feedback>{

	@Autowired  
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Feedback load(long id) {
		return (Feedback) sessionFactory.getCurrentSession().get(Feedback.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Feedback> page(Page page) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_feedback");
		
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Feedback> list(String condition, String orderName) {
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_feedback");
		
		if(condition != null && !condition.equalsIgnoreCase("")) {
			sb.append(" where ");
			sb.append(condition);
		}
		
		if (orderName != null && !orderName.equalsIgnoreCase("")) {
			sb.append(" order by ");
			sb.append(orderName);
		}
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addEntity(Feedback.class);
		return query.list();
	}
	
	@Override
	public int count(String condition) {
		
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from t_feedback ");
		if(condition != null && !condition.equalsIgnoreCase("")) {
			sb.append(" where ");
			sb.append(condition);
		}
		SQLQuery query = session.createSQLQuery(sb.toString());
		return ((Number)query.uniqueResult()).intValue(); 
	}
	
	@Override
	public long save(Feedback feedback) {
		return (Long)sessionFactory.getCurrentSession().save(feedback);
	}
	
	@Override
	public void update(Feedback feedback) {
		sessionFactory.getCurrentSession().update(feedback);
	}
	
	@Override
	public void delete(Feedback feedback) {
		sessionFactory.getCurrentSession().delete(feedback);
	}

}
