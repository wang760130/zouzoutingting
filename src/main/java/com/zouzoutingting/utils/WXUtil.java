package com.zouzoutingting.utils;

import org.apache.commons.httpclient.NameValuePair;

import com.zouzoutingting.components.encrypt.MD5;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 微信支付辅助工具类
 */
public class WXUtil {

	public static String getNonceStr() {
		Random random = new Random();
		return MD5.Md5Encryt(String.valueOf(random.nextInt(10000)));
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static String getRequestXml(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set<?> set = parameters.entrySet();
		Iterator<?> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)
					|| "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 签名生成算法
	 * @param charSet 编码
	 * @param parameters 签名参数
	 * @param key 账户密钥
	 * @return
	 */
	public static String createSign(String charSet,
			SortedMap<String, String> parameters,String key) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5.Md5Encryt(sb.toString(), charSet).toUpperCase();
		return sign;
	}
	
  /**
   * 微信支付签名
   * @param sParaTemp
   * @param partnerKey
   * @return
   */
  public static String wxSign(Map<String, String> sParaTemp, String partnerKey) {
    ArrayList<String> mKeyArray = new ArrayList<String>();
    for (String key : sParaTemp.keySet()) {
      mKeyArray.add(key);
    }
    Collections.sort(mKeyArray);
    StringBuilder mDataBuilder = new StringBuilder();
    for (int i = 0; i < mKeyArray.size(); i++) {
      mDataBuilder.append(mKeyArray.get(i) + "=").append(sParaTemp.get(mKeyArray.get(i)))
          .append("&");
    }
    mDataBuilder.append("key=" + partnerKey);
    return MD5.Md5Encryt(mDataBuilder.toString(), "UTF-8").toUpperCase();
  }
  
  /**
   * NameValuePair类型的 微信加密
   * @param charSet
   * @param linkdata
   * @param partnerKey
   * @return
   */
  public static String wxSignByNvPair(String charSet,List<NameValuePair> linkdata,String partnerKey){
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < linkdata.size(); i++) {
      String k = linkdata.get(i).getName();
      Object v = linkdata.get(i).getValue();
      if (null != v && !"".equals(v) && !"sign".equals(k)
              && !"key".equals(k)) {
          sb.append(k + "=" + v + "&");
      }
    }
    sb.append("key=" + partnerKey);
    String sign = MD5.Md5Encryt(sb.toString(), charSet).toUpperCase();
    return sign;
  }
  
}
