package com.zouzoutingting.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.City;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月2日
 */

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:spring-hibernate.xml"}) 
public class CityDaoImplTest {
	
	@Autowired
	IDao<City> cityDao = null;
	
	@Test
	public void loadTest() {
		City city = cityDao.load(4);
		System.out.println(city);
	}
	
	@Test
	public void queryTest() {
		
	}
	
	@Test
	public void saveTest() {
		City city = new City();
		city.setName("西安");
		cityDao.save(city);
	}
	
	@Test
	public void updateTest() {
		City city = cityDao.load(4);
		city.setEname("xian");
		cityDao.update(city);
	}
	
	@Test
	public void deleteTest() {
		City city = cityDao.load(4);
		cityDao.delete(city);
	}
}
