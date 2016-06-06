package com.zouzoutingting.service;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月5日
 */
public interface IVcCodeService {

	public void addVcCode(long phone, int code);
	
	public boolean checkVcCode(long phone, int code);

}
