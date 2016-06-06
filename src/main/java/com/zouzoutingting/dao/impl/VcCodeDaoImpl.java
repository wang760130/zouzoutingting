package com.zouzoutingting.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.VcCode;
import com.zouzoutingting.utils.Page;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月5日
 */
@Transactional
@Repository(value="vcCodeDao")
public class VcCodeDaoImpl implements IDao<VcCode> {

	@Autowired  
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public VcCode load(long id) {
		return (VcCode) sessionFactory.getCurrentSession().get(VcCode.class, id);
	}

	@Override
	public List<VcCode> page(Page page) {
		Session session = sessionFactory.getCurrentSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_vccode");
		
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
		query.addEntity(VcCode.class);
		return query.list();
	}

	@Override
	public int count(String condition) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from t_vccode ");
		if(condition != null && !condition.equalsIgnoreCase("")) {
			sb.append(" where ");
			sb.append(condition);
		}
		SQLQuery query = session.createSQLQuery(sb.toString());
		return ((Number)query.uniqueResult()).intValue(); 
	}

	@Override
	public long save(VcCode vcCode) {
		return (Long)sessionFactory.getCurrentSession().save(vcCode);
	}

	@Override
	public void update(VcCode vcCode) {
		sessionFactory.getCurrentSession().update(vcCode);
	}

	@Override
	public void delete(VcCode vcCode) {
		sessionFactory.getCurrentSession().delete(vcCode);
	}

}
