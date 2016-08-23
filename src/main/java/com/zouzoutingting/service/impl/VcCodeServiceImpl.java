package com.zouzoutingting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.components.dao.Page;
import com.zouzoutingting.model.VcCode;
import com.zouzoutingting.service.IVcCodeService;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月5日
 */
@Service("vcCodeService")
public class VcCodeServiceImpl implements IVcCodeService {
	
	private static final short IS_USED = 0;
	private static final short IS_NOT_USERD = 1;
	
	@Autowired
	private IDao<VcCode> vcCodeDao = null;
	
	@Override
	public void addVcCode(long phone, int code) {
		VcCode vcCode = new VcCode();
		vcCode.setCode(code);
		vcCode.setPhone(phone);
		vcCode.setUsed(IS_NOT_USERD);
		vcCode.setAddtime(System.currentTimeMillis());
		vcCodeDao.save(vcCode);
	}
	
	@Override
	public boolean checkVcCode(long phone, int code) {
		boolean result = false;
		Page page = new Page();
		String condition = "code = " + code + " and phone = " + phone + " and used = " + IS_NOT_USERD;
		page.setCondition(condition);
		page.setPageNo(1);
		page.setPageSize(1);
		List<VcCode> list = vcCodeDao.page(page);
		if(list != null && list.size() > 0) {
			VcCode vcCode = list.get(0);
			vcCode.setUsed(IS_USED);
			vcCodeDao.update(vcCode);
			result = true;
		}
		return result;
	}

}
