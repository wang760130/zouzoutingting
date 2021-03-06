package com.zouzoutingting.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.components.dao.Page;
import com.zouzoutingting.model.User;
import com.zouzoutingting.service.IUserService;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月4日
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IDao<User> userDao = null;
	
	@Override
	public User getUserByPhone(long phone) {
		User user = null;
		String condition = "phone = " + phone;
		Page page = new Page();
		page.setCondition(condition);
		page.setPageNo(1);
		page.setPageSize(1);
		List<User> userList = userDao.page(page);
		if(userList != null && userList.size() > 0) {
			user = userList.get(0);
		}
		return user;
	}
	
	@Override
	public User getUserById(long id) {
		return userDao.load(id);
	}

	@Override
	public long createUserByPhone(long phone) {
		User user = new User();
		user.setPhone(phone);
		user.setAddtime(new Date());
		return userDao.save(user);
	}
	
}
