package com.zouzoutingting.utils.alipay;

import org.apache.log4j.Logger;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.*;


/* *
 * 功能：支付宝接口公用函数类详细：该类是请求、 通知返回两个文件所调用的公用函数核心处理文件
 */
public class AlipayCore {

	private static final Logger LOGGER = Logger.getLogger(AlipayCore.class);
	public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter YYYYMMDD = DateTimeFormat.forPattern("yyyyMMdd");

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param array
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> array) {

		Map<String, String> result = new HashMap<String, String>();

		if (array == null || array.size() <= 0) {
			return result;
		}

		for (Map.Entry<String, String> entry : array.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")
					|| key.startsWith("zztt_")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value + "";
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		System.out.println("@@@ " + prestr);
		return prestr;
	}


}
