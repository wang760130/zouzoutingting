package com.zouzoutingting.dao;

import java.util.List;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月29日
 */
public interface IDao<T> {
	
	public T load(long id);
	
	public List<T> query(String query);
	
	public void save(T t);
	
	public void update(T t);
	
	public void delete(T t);
	
}
	