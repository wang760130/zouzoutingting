package com.zouzoutingting.dao;

import java.util.List;

import com.zouzoutingting.utils.Page;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月29日
 */
public interface IDao<T> {
	
	public T load(long id);
	
	public List<T> page(Page page);
	
	public int count(String condition);
	
	public void save(T t);
	
	public void update(T t);
	
	public void delete(T t);
	
}
	