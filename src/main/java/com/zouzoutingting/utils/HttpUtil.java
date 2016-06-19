package com.zouzoutingting.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 这个httpclient比较旧，以后有空再更新
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月5日
 */
public class HttpUtil {
	private static final Logger logger = Logger.getLogger(HttpUtil.class);

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
	 * @param params
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static String post(String url, Map<String, String> params) throws ParseException, IOException {
		HttpClient httpclient = null;
		String body = null;
		try {
			httpclient = new DefaultHttpClient();
			HttpPost post = postForm(url, params);
			body = invoke(httpclient, post);
		} catch(IOException e) {
			throw e;
		} catch(ParseException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return body;
	}

	public static String post(final String url, String body) {
		CloseableHttpClient httpclient = null;
		HttpPost httpost = null;
		try {
			httpclient = HttpClients.custom().build();
			httpost = new HttpPost(url);
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT)
					.setConnectTimeout(CONNECTION_TIMEOUT).build();
			httpost.setConfig(requestConfig);
			StringEntity sey = new StringEntity(body, Charset.forName("UTF-8"));
			httpost.setEntity(sey);
			return invoke(httpclient, httpost);
		} catch (Exception ex) {
			logger.error("http post:"+url+" Error", ex);
		} finally {
			if (httpost != null) {
				httpost.releaseConnection();
			}
		}
		return null;
	}

	/**
	 * 根据url和参数发起http get请求
	 * 
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static String get(String url) throws ParseException, IOException {
		HttpClient httpclient = null;
		String body = null;
		try {
			httpclient = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			body = invoke(httpclient, get);
		} catch(IOException e) {
			throw e;
		} catch(ParseException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	
		return body;
	}
	
	/**
	 * 下载文件保存到本地 
	 * @param path 文件保存位置 
	 * @param url 文件地址 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void downloadFile(String path, String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = null;
		
		try {
			httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				
				BufferedOutputStream bos = null;
				FileOutputStream fos = null;
				
				try {
					File file = new File(path);
					if(!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					fos = new FileOutputStream(path);
					bos = new BufferedOutputStream(fos);
					bos.write(result);
				} catch(IOException e) {
					throw e;
				} finally {
					if(bos != null) {
						bos.close();
					}
					if(fos != null) {
						fos.close();
					}
				}
			}
		} catch(ClientProtocolException e) {
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	private static String invoke(HttpClient httpclient,
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