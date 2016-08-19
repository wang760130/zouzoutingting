package com.zouzoutingting.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.Spot;

/**
 * Created by zhangyong on 16/4/8.
 * 景点讲解数据层
 */
@Transactional
@Repository(value="spotDao")
public class SpotDaoImpl extends DaoImpl<Spot> implements IDao<Spot> {}
