package com.zouzoutingting.service;

import com.zouzoutingting.model.AppVersion;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月3日
 */
public interface IAppVersionService {
	
	/**
	 * 根据操作系统获取最新版本
	 * @param os
	 * @return
	 */
	public AppVersion getNewAppVersionByOs(String os);

}
