package com.zouzoutingting.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.City;

/**
 * @author Jerry Wang
 * @Email jerry002@126.com
 * @date 2016年4月2日
 */
@Repository(value="cityDao")
public class CityDaoImpl implements IDao<City> {
	
	@Autowired  
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public City load(long id) {
		return (City) sessionFactory.getCurrentSession().get(City.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<City> query(String a) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(a);
		
		return query.list();
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
