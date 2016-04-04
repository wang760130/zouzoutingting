package com.zouzoutingting.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.common.Global;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月4日
 */
public class HttpTestUtils {
	
	public static JSONObject testUrl(String path) throws Exception{
		URL url = new URL(path);
		HttpURLConnection  conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		
		byte[] inputBytes = input2byte(conn.getInputStream());
		byte[] decryptBytes = DESCipher.decrypt(inputBytes, Global.RESPONSE_DESKEY);
		byte[] unzipBytes = GZipUtils.decompress(decryptBytes);
		String str = new String(unzipBytes);
        return  JSON.parseObject(str);
	}
	
	public static JSONObject testUrl(String path,String param) throws Exception{
		URL url = new URL(path);
		HttpURLConnection  conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		String query =  RequestParamUtil.encryptParan(param);
		conn.getOutputStream().write(query.getBytes());
		conn.connect();

		byte[] inputBytes = input2byte(conn.getInputStream());
		byte[] decryptBytes = DESCipher.decrypt(inputBytes, Global.RESPONSE_DESKEY);
		byte[] unzipBytes = GZipUtils.decompress(decryptBytes);
		String str = new String(unzipBytes);
        return  JSON.parseObject(str);
	}
	
	public static final byte[] input2byte(InputStream inStream)
			throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

}
