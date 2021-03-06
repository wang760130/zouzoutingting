package com.zouzoutingting.service;

import java.util.List;

import com.zouzoutingting.components.dao.Page;
import com.zouzoutingting.model.City;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月28日
 */
public interface ICityService {
	
	public City load(long id);
	
	public City getCityByCode(int cityCode);
	
	public List<City> getAll();
	
	public List<City> page(Page page);
	
	public int count(String condition);
	
	public void save(City city);
	
	public void update(City city);
	
	public void delete(City city);

}
