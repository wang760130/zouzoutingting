package com.zouzoutingting.service;

import java.util.List;

import com.zouzoutingting.model.City;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月28日
 */
public interface ICityService {
	
	public City load(long id);
	
	public List<City> query(String query);
	
	public void save(City city);
	
	public void update(City city);
	
	public void delete(City city);

}
