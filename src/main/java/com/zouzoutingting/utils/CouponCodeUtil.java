package com.zouzoutingting.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * Created by zhangyong on 16/7/31.
 */
public class CouponCodeUtil {
    public static final String ALLCHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length  随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
    	FileOutputStream fos = new FileOutputStream("coupon.txt"); 
        PrintStream ps = new PrintStream(fos); 
        System.setOut(ps); 
        for(int i = 0;i < 15000; i++) {
        	System.out.println("INSERT INTO `zouzoutingting`.`t_coupon` (`couponid`, `code`, `amount`, `state`, `starttime`, `endtime`) VALUES (NULL, '"+generateString(8)+"', '30', '0', '2016-08-23 00:00:00', '2017-08-23 00:00:00');");
        }
    }
}
