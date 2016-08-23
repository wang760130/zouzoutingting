package com.zouzoutingting.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zouzoutingting.components.dao.DaoImpl;
import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.model.User;


/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月4日
 */
@Transactional
@Repository(value="userDao")
public class UserDaoImpl extends DaoImpl<User> implements IDao<User> {}
