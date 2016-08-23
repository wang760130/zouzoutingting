package com.zouzoutingting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.components.dao.Page;
import com.zouzoutingting.model.AppVersion;
import com.zouzoutingting.service.IAppVersionService;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月3日
 */
@Service("appVersionService")
public class AppVersionServiceImpl implements IAppVersionService {

	@Autowired
	private IDao<AppVersion> appVersionDao = null;
	
	/**
	 * 根据操作系统获取最新版本
	 * @param os
	 * @return
	 */
	@Override
	public AppVersion getNewAppVersionByOs(String os) {
		Page page = new Page();
		String condition = "os='" + os + "'";
		page.setCondition(condition);
		page.setPageNo(1);
		page.setPageSize(1);
		page.setOrderName("version desc");
		
		AppVersion appVersion = null;
		List<AppVersion> appVersionList = appVersionDao.page(page);
		if(appVersionList != null && appVersionList.size() > 0) {
			appVersion = appVersionList.get(0);
		}
		return appVersion;
	}

}
