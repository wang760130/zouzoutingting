package com.zouzoutingting.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.City;
import com.zouzoutingting.utils.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jerry Wang
 * @Email jerry002@126.com
 * @date 2016年4月2日
 */
@Transactional
@Repository(value="cityDao")
public class CityDaoImpl implements IDao<City> {
	
	@Autowired  
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
//	ParameterizedType pt = (ParameterizedType) this.clazz.getGenericSuperclass();
//    this.clazz = (Class<T>)pt.getActualTypeArguments()[0];
	
	@Override
	public City load(long id) {
		return (City) sessionFactory.getCurrentSession().get(City.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<City> page(Page page) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_city");
		
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
		sb.append("select count(*) from t_city ");
		if(condition != null && !condition.equalsIgnoreCase("")) {
			sb.append(" where ");
			sb.append(condition);
		}
		SQLQuery query = session.createSQLQuery(sb.toString());
		return ((Number)query.uniqueResult()).intValue(); 
	}
	
	@Override
	public void save(City city) {
		sessionFactory.getCurrentSession().save(city);
	}
	
	@Override
	public void update(City city) {
		sessionFactory.getCurrentSession().update(city);
	}
	
	@Override
	public void delete(City city) {
		sessionFactory.getCurrentSession().delete(city);
	}
	
}
