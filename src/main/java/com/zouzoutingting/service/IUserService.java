package com.zouzoutingting.service;

import com.zouzoutingting.model.User;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月4日
 */
public interface IUserService {
	
	public User getUserByPhone(long phone);
	
	public long createUserByPhone(long phone);
}
