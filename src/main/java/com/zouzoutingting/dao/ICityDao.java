package com.zouzoutingting.dao;

import org.apache.ibatis.annotations.Param;

import com.zouzoutingting.model.City;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月29日
 */
public interface ICityDao {
	
	public City load(@Param("id")long id) throws Exception;
	
    public long insert(City city) throws Exception;
    
    public boolean update(City city) throws Exception;
	
	public boolean delete(long id) throws Exception;
    
//    public List<City> page(Page page) throws Exception;
	
	public int count(String condition) throws Exception;
	
}
