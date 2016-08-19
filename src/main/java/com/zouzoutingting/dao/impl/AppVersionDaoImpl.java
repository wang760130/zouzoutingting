package com.zouzoutingting.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.AppVersion;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月3日
 */

@Transactional
@Repository(value="appVersionDao")
public class AppVersionDaoImpl extends DaoImpl<AppVersion> implements IDao<AppVersion> {}
