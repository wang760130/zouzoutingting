package com.zouzoutingting.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 这个httpclient比较旧，以后有空再更新
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月5日
 */
public class HttpUtil {
	private static final int CONNECTION_TIMEOUT = 10 * 1000;
	private static final int SO_TIMEOUT = 5 * 1000;

	private static final HttpParams httpParams = new BasicHttpParams();

	static {
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,CONNECTION_TIMEOUT);
		httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
	}

	/**
	 * 根据url和参数发起http post请求
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static String post(String url, Map<String, String> params) throws ParseException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		HttpPost post = postForm(url, params);
		body = invoke(httpclient, post);
		httpclient.getConnectionManager().shutdown();
		return body;
	}

	public static String get(String url) throws ParseException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);
		httpclient.getConnectionManager().shutdown();
		return body;
	}

	private static String invoke(DefaultHttpClient httpclient,
			HttpUriRequest httpost) throws ParseException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		HttpResponse response =  httpclient.execute(httpost);
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} else {
			map.put("success", "false");
			map.put("message", response.getStatusLine().getStatusCode());
			map.put("entity", new Object());
			JSONObject jsonObject = JSONObject.fromObject(map);  
			return jsonObject.toString();
		}
	}

	private static HttpPost postForm(String url, Map<String, String> params) throws UnsupportedEncodingException {
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		if (params != null) {
			for (String key : keySet) {
				pairList.add(new BasicNameValuePair(key, params.get(key)));
			}
		}

		httpost.setEntity(new UrlEncodedFormEntity(pairList, HTTP.UTF_8));

		return httpost;
	}
	
}