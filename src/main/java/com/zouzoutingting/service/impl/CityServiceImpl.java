package com.zouzoutingting.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zouzoutingting.dao.ICityDao;
import com.zouzoutingting.service.ICityService;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月28日
 */

@Service("cityService")
public class CityServiceImpl implements ICityService {

	 @Resource
	 private ICityDao cityDao;  
}
