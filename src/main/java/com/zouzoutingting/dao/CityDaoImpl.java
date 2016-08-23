package com.zouzoutingting.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.components.dao.DaoImpl;
import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.model.City;

/**
 * @author Jerry Wang
 * @Email jerry002@126.com
 * @date 2016年4月2日
 */
@Transactional
@Repository(value="cityDao")
public class CityDaoImpl extends DaoImpl<City> implements IDao<City> {}
