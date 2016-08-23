package com.zouzoutingting.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.components.dao.DaoImpl;
import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.model.ViewSpot;

/**
 * Created by zhangyong on 16/4/7.
 */
@Transactional
@Repository(value="viewSpotDao")
public class ViewSpotDaoImpl extends DaoImpl<ViewSpot> implements IDao<ViewSpot> {}
