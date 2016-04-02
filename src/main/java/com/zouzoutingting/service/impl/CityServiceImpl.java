package com.zouzoutingting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.City;
import com.zouzoutingting.service.ICityService;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月28日
 */

@Service("cityService")
public class CityServiceImpl implements ICityService {
	
	@Autowired
	private IDao<City> cityDao;  
	
	@Override
	public City load(long id) {
		return cityDao.load(id);
	}
	
	@Override
	public List<City> query(String query) {
		return cityDao.query(query);
	}
	
	@Override
	public void save(City city) {
		cityDao.save(city);
	}
	
	@Override
	public void update(City city) {
		cityDao.update(city);
	}
	
	@Override
	public void delete(City city) {
		cityDao.delete(city);
	}
	 
}
