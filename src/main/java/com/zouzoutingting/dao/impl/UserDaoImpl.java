package com.zouzoutingting.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.User;
import com.zouzoutingting.utils.Page;


/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月4日
 */
@Transactional
@Repository(value="userDao")
public class UserDaoImpl implements IDao<User> {

	@Autowired  
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public User load(long id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> page(Page page) {
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_user");
		
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
		query.addEntity(User.class);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> list(String condition, String orderName) {
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_user");
		
		if(condition != null && !condition.equalsIgnoreCase("")) {
			sb.append(" where ");
			sb.append(condition);
		}
		
		if (orderName != null && !orderName.equalsIgnoreCase("")) {
			sb.append(" order by ");
			sb.append(orderName);
		}
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addEntity(User.class);
		return query.list();
	}
	
	@Override
	public int count(String condition) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from t_user ");
		if(condition != null && !condition.equalsIgnoreCase("")) {
			sb.append(" where ");
			sb.append(condition);
		}
		SQLQuery query = session.createSQLQuery(sb.toString());
		return ((Number)query.uniqueResult()).intValue(); 
	}

	@Override
	public long save(User user) {
		return (Long)sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public void delete(User user) {
		sessionFactory.getCurrentSession().delete(user);
	}

}
