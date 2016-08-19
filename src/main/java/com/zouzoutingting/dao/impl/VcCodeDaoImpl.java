package com.zouzoutingting.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.VcCode;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月5日
 */
@Transactional
@Repository(value="vcCodeDao")
public class VcCodeDaoImpl extends DaoImpl<VcCode> implements IDao<VcCode> {}
