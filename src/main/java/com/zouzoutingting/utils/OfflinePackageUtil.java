package com.zouzoutingting.utils;

import com.zouzoutingting.common.Global;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

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
    public static String generateToken(String data) {

        byte[] bytes = new byte[0];
        try {
            bytes = DES.encrypt(String.valueOf(data).getBytes(Global.DEFUALT_CHARSET),Global.OFFLINE_DESKEY);
        } catch (Exception e) {
            logger.error("des 加密 offline error "+data, e);
        }
        return new String(Base64.encodeBase64(bytes));
    }

}
