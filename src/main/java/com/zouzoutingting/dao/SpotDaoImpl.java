package com.zouzoutingting.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.components.dao.DaoImpl;
import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.model.Spot;

/**
 * Created by zhangyong on 16/4/8.
 * 景点讲解数据层
 */
@Transactional
@Repository(value="spotDao")
public class SpotDaoImpl extends DaoImpl<Spot> implements IDao<Spot> {}
