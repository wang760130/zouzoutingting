package com.zouzoutingting.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.components.dao.DaoImpl;
import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.model.AppVersion;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月3日
 */

@Transactional
@Repository(value="appVersionDao")
public class AppVersionDaoImpl extends DaoImpl<AppVersion> implements IDao<AppVersion> {}
