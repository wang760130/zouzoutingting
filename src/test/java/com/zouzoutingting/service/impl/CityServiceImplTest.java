package com.zouzoutingting.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zouzoutingting.components.dao.Page;
import com.zouzoutingting.model.City;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月3日
 */

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class CityServiceImplTest {

	@Autowired
	CityServiceImpl cityService = null;
	
	@Test
	public void loadTest() {
		City city = cityService.load(4);
		System.out.println(city);
	}
	
	@Test
	public void pageTest() {
		Page page = new Page();
		page.setPageNo(2);
		page.setPageSize(10);
		List<City> cityList = cityService.page(page);
		for(City city : cityList) {
			System.out.println(city.getEname());
		}
	}
	
	@Test
	public void countTest() {
		System.out.println(cityService.count(""));
	}
	
	@Test
	public void saveTest() {
		City city = new City();
		city.setName("西安");
		cityService.save(city);
	}
	
	@Test
	public void updateTest() {
		City city = cityService.load(4);
		city.setEname("xian");
		cityService.update(city);
	}
	
	@Test
	public void deleteTest() {
		City city = cityService.load(4);
		cityService.delete(city);
	}
}
