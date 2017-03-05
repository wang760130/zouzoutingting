package com.zouzoutingting.dao;

import com.zouzoutingting.components.dao.DaoImpl;
import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.model.Config;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangyong on 17/2/4.
 */
@Transactional
@Repository(value="configDao")
public class ConfigDaoImpl extends DaoImpl<Config> implements IDao<Config> {
}
