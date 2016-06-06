package com.zouzoutingting.dao.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.City;
import com.zouzoutingting.utils.Page;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月2日
 */

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class CityDaoImplTest {
	
	@Autowired
	IDao<City> cityDao = null;
	
	@Test
	public void loadTest() {
		City city = cityDao.load(4);
		System.out.println(city);
	}
	
	@Test
	public void pageTest() {
		Page page = new Page();
		page.setPageNo(2);
		page.setPageSize(10);
		List<City> cityList = cityDao.page(page);
		for(City city : cityList) {
			System.out.println(city.getEname());
		}
	}
	
	@Test
	public void countTest() {
		System.out.println(cityDao.count(""));
	}
	
	@Test
	public void saveTest() {
		City city = new City();
		city.setName("渭南");
		long id = cityDao.save(city);
		System.out.println(id);
	}
	
	@Test
	public void batchSaveTest() {
		City city = null;
		for(int i = 0; i < 1000; i++){
			city = new City();
			city.setEname("西安" + i);
			cityDao.save(city);
		}
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
