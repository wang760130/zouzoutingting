package com.zouzoutingting.components.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.Table;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.utils.Page;

/**
 * 所有Dao均实现此类
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年8月14日
 */

@Transactional
public class DaoImpl<T> implements IDao<T> {
	
	private Class<T> clazz;
	private String table = "";
	
	@Autowired  
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public DaoImpl() {
		Type type = getClass().getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			type = getClass().getSuperclass().getGenericSuperclass();
		}
		this.clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
		
        Table annotation = (Table)clazz.getAnnotation(Table.class);
        this.table = annotation.name();
	}
	
	@Override
	public long save(T t) {
		return (Long)sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public void update(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

	@Override
	public void delete(T t) {
		sessionFactory.getCurrentSession().delete(t);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T load(long id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> page(Page page) {
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(table);
		
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
		query.addEntity(clazz);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> list(String condition, String orderName) {
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(table);
		
		if(condition != null && !condition.equalsIgnoreCase("")) {
			sb.append(" where ");
			sb.append(condition);
		}
		
		if (orderName != null && !orderName.equalsIgnoreCase("")) {
			sb.append(" order by ");
			sb.append(orderName);
		}
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addEntity(clazz);
		return query.list();
	}

	@Override
	public int count(String condition) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from ").append(table);
		if(condition != null && !condition.equalsIgnoreCase("")) {
			sb.append(" where ");
			sb.append(condition);
		}
		SQLQuery query = session.createSQLQuery(sb.toString());
		return ((Number)query.uniqueResult()).intValue(); 
	}

}
