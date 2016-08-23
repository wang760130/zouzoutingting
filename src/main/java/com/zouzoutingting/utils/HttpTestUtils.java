package com.zouzoutingting.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.components.encrypt.DES;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

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
		byte[] decryptBytes = DES.decrypt(inputBytes, Global.DESKEY);
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
		String query = RequestParamUtil.encryptParan(param);
//		String query = param;
		conn.getOutputStream().write(query.getBytes());
		conn.connect();

		InputStream is = conn.getInputStream();
		byte[] inputBytes = input2byte(is);
		byte[] decryptBytes = DES.decrypt(inputBytes, Global.DESKEY);
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

	public static String httpClientPost(String url, Map<String, String> params, Map<String, String> headers) {
		String result = "";
		HttpPost httppost = new HttpPost(url);
//		log.info("http post url=" + url);
		List<NameValuePair> paramsPair = new ArrayList<NameValuePair>();

		// 设置Http Post数据
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
//				log.info("~~~~~~~~" + entry.getKey() + "=" + entry.getValue() + "~~~~~~~~");
				paramsPair.add(pair);
			}
		}
		if(headers!=null){
			for(String hkey : headers.keySet()){
				httppost.addHeader(hkey, headers.get(hkey));
			}
		}
		try {
			httppost.setEntity(new UrlEncodedFormEntity(paramsPair, HTTP.UTF_8));
			RequestConfig config = RequestConfig.custom().setConnectTimeout(1500).setSocketTimeout(6000).build();
			CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(config).build();
			//设置超时时间
			CloseableHttpResponse response = httpclient.execute(httppost);
			// 上面两行可以得到指定的Header
			if (response.getStatusLine().getStatusCode() == 200) {// 如果状态码为200,就是正常返回
				result = EntityUtils.toString(response.getEntity());
//				log.info("httpClientPost: url=" + url + " ,result =" + result);
			} else {
//				log.error("httpClientPost error! url=" + url + " ,=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
//			log.error("httpClientPost error", e);
		}
		return result;
	}

}
