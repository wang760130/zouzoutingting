package com.zouzoutingting.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.AppVersion;
import com.zouzoutingting.utils.Page;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月3日
 */

@Transactional
@Repository(value="appVersionDao")
public class AppVersionDaoImpl implements IDao<AppVersion>{
	
	@Autowired  
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	public AppVersion load(long id) {
		return (AppVersion) sessionFactory.getCurrentSession().get(AppVersion.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AppVersion> page(Page page) {
		
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_appversion");
		
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
		query.addEntity(AppVersion.class);
		return query.list();
	}
	
	@Override
	public int count(String condition) {
		
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from t_appversion ");
		if(condition != null && !condition.equalsIgnoreCase("")) {
			sb.append(" where ");
			sb.append(condition);
		}
		SQLQuery query = session.createSQLQuery(sb.toString());
		return ((Number)query.uniqueResult()).intValue(); 
	}
	
	@Override
	public void save(AppVersion appVersion) {
		sessionFactory.getCurrentSession().save(appVersion);
	}
	
	@Override
	public void update(AppVersion appVersion) {
		sessionFactory.getCurrentSession().update(appVersion);
	}
	
	@Override
	public void delete(AppVersion appVersion) {
		sessionFactory.getCurrentSession().delete(appVersion);
	}

}
