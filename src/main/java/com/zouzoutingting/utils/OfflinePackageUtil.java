package com.zouzoutingting.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.zouzoutingting.common.Global;

/**
 * Created by zhangyong on 16/7/28.
 */
public class OfflinePackageUtil {
    private static final Logger logger = Logger.getLogger(OfflinePackageUtil.class);

    /**
     * 离线包地址des加密
     * @param data 离线包地址
     * @return
     * @throws Exception
     */
    public static String generateOffline(String data) {

        byte[] bytes = new byte[0];
        try {
            bytes = DES.encrypt(String.valueOf(data).getBytes(Global.DEFUALT_CHARSET),Global.OFFLINE_DESKEY);
        } catch (Exception e) {
            logger.error("des encrypt offline error "+data, e);
        }
        return new String(Base64.encodeBase64(bytes));
    }
    
    /**
     * 解密
     * @param data
     * @return
     */
    public static String decryptOffline(String data) {
    	
    	byte[] bytes = Base64.decodeBase64(data);
    	try {
    		bytes = DES.decrypt(bytes, Global.OFFLINE_DESKEY);
		} catch (Exception e) {
			 logger.error("des decrypt offline error "+data, e);
		}
    	return new String(bytes);
    }
    
}
