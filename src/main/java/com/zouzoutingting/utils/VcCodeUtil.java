package com.zouzoutingting.utils;

/**
 * VC（Verification Code - 验证码）工具
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月1日
 */
public class VcCodeUtil {
	
	/**
	 * vcCode 的过期时间，30分钟
	 */
	public static final long VC_EXPIRE_TIME = 30 * 60 * 1000;
	
	public static final int VCCODE_LENGTH = 6;

	/**
	 * 生成的vcCode，随机6位数字
	 * @return
	 */
	public static int genVcCode() {
		return (int) (100000 + Math.random() * 900000);
	}
	
	public static boolean checkVcCode(String vcCode) {
		if(ValidityUtil.isNum(vcCode, VCCODE_LENGTH)) {
			return true;
		}
		return false;
	}
	
}
