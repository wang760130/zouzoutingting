package com.zouzoutingting.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.zouzoutingting.common.Global;
import org.apache.commons.codec.binary.Base64;

/**
 * DES加密
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月4日
 */
public class DES {

	/**
	 * 加密
	 */
	public static byte[] encrypt(byte[] datasource, String password)
			throws Exception {
		SecureRandom random = new SecureRandom();
		DESKeySpec desKey = new DESKeySpec(password.getBytes(Global.DEFUALT_CHARSET));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(desKey);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
		return cipher.doFinal(datasource);
	}

	/**
	 * 解密
	 */
	public static byte[] decrypt(byte[] src, String password) throws Exception {
		SecureRandom random = new SecureRandom();
		DESKeySpec desKey = new DESKeySpec(password.getBytes(Global.DEFUALT_CHARSET));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(desKey);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		return cipher.doFinal(src);
	}

	public static void main(String[] args) throws Exception{
		String data = "6CWEvKZBXHw=";
		byte[] ss = Base64.decodeBase64(data);
		byte[] ss1 = decrypt(ss, "offlineUrl_zztt");
		System.out.println(ss1);
	}
}