package com.zouzoutingting.utils.alipay;

import com.zouzoutingting.common.Global;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 *
 * 阿里支付工具类
 */
public class AliParamCore {
  private static final Logger logger = Logger.getLogger(AliParamCore.class);


  public static String getFullUrlParam(Map<String, String> map){
    String sign = getSign(map, Global.ALI_PAY_PARTNER_PRIVATE_KEY);
    String params = getParasm(map, false);
//    logger.info("verify sign " + RSA.verify(params, sign, Global.ALI_PAY_PARTNER_PUB_KEY, Global.ALI_PAY_INPUT_CHARSET));
    return params+"&sign=\""+sign+"\"&sign_type=\"" + "RSA" + "\"";

  }

//  /**
//   * 构造支付订单参数信息
//   * @param map 支付订单参数
//   * @return
//   */
//  public static String buildOrderParam(Map<String, String> map) {
//    List<String> keys = new ArrayList<String>(map.keySet());
//
//    StringBuilder sb = new StringBuilder();
//    for (int i = 0; i < keys.size() - 1; i++) {
//      String key = keys.get(i);
//      String value = map.get(key);
//      sb.append(buildKeyValue(key, value, true));
//      sb.append("&");
//    }
//
//    String tailKey = keys.get(keys.size() - 1);
//    String tailValue = map.get(tailKey);
//    sb.append(buildKeyValue(tailKey, tailValue, true));
//
//    return sb.toString();
//  }

  private static String getParasm(Map<String, String> map, boolean needurlencode){
    List<String> keys = new ArrayList<String>(map.keySet());
    // key排序
    Collections.sort(keys);

    StringBuilder authInfo = new StringBuilder();
    for (int i = 0; i < keys.size() - 1; i++) {
      String key = keys.get(i);
      String value = map.get(key);
      authInfo.append(buildKeyValue(key, value, needurlencode));
      authInfo.append("&");
    }

    String tailKey = keys.get(keys.size() - 1);
    String tailValue = map.get(tailKey);
    authInfo.append(buildKeyValue(tailKey, tailValue, needurlencode));
    return authInfo.toString();
  }
  /**
   * 对支付参数信息进行签名
   *
   * @param map
   *            待签名授权信息
   *
   * @return
   */
  public static String getSign(Map<String, String> map, String rsaKey) {
    String authInfo = getParasm(map, false);
    logger.info("befer rsa data:" + authInfo);
    String oriSign = RSA.sign(authInfo, rsaKey, Global.ALI_PAY_INPUT_CHARSET);
    String encodedSign = "";

    try {
      encodedSign = URLEncoder.encode(oriSign, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      logger.error("get sign error", e);
    }
    return encodedSign;
//    return  oriSign;
  }

  /**
   * 拼接键值对
   *
   * @param key
   * @param value
   * @param isEncode
   * @return
   */
  private static String buildKeyValue(String key, String value, boolean isEncode) {
    StringBuilder sb = new StringBuilder();
    sb.append(key);
    sb.append("=");
    if (isEncode) {
      try {
        sb.append("\"").append(URLEncoder.encode(value, "UTF-8")).append("\"");
      } catch (UnsupportedEncodingException e) {
        sb.append(value);
      }
    } else {
      sb.append("\"").append(value).append("\"");
    }
    return sb.toString();
  }
  
}
