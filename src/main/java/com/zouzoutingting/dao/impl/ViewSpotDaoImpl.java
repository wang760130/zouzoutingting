package com.zouzoutingting.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.ViewSpot;

/**
 * Created by zhangyong on 16/4/7.
 */
@Transactional
@Repository(value="viewSpotDao")
public class ViewSpotDaoImpl extends DaoImpl<ViewSpot> implements IDao<ViewSpot> {}
