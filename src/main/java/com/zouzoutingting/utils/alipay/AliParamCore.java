package com.zouzoutingting.utils.alipay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zouzoutingting.common.Global;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 阿里支付工具类
 */
public class AliParamCore {
  private static final Logger logger = Logger.getLogger(AliParamCore.class);

  /**
   * 去空，加签
   * @param sParaTemp
   * @param privateKey
   * @return
   * @throws UnsupportedEncodingException
   */
  public static String AppBuildAliRequestStr(Map<String, String> sParaTemp,String privateKey)throws
          UnsupportedEncodingException {
//    Map<String, String> ret = new HashMap<String, String>();
//    // 除去数组中的空值和签名参数
    Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
//    ret.put("_input_charset", sPara.get("_input_charset"));
//    ret.put("body",sPara.get("body"));
//    ret.put("notify_url", sPara.get("notify_url"));
//    ret.put("out_trade_no",sPara.get("out_trade_no"));
//    ret.put("partner",sPara.get("partner"));
//    ret.put("payment_type", sPara.get("payment_type"));
//    ret.put("seller_id",sPara.get("seller_id"));
//    ret.put("service",sPara.get("service"));
//    ret.put("subject",sPara.get("subject"));
//    ret.put("total_fee",sPara.get("total_fee"));
//    ObjectMapper mapper = new ObjectMapper();
//    String value = null;
//    try {
//      value = mapper.writeValueAsString(ret);
//    } catch (JsonProcessingException e) {
//      logger.error("阿里支付转json error", e);
//    }
//    logger.info("APP加密前串：" + value);
//    String mysign = RSA.sign(value, privateKey, Global.ALI_PAY_INPUT_CHARSET);
//    ret.put("sign",URLEncoder.encode(mysign, Global.ALI_PAY_INPUT_CHARSET));
//    ret.put("sign_type","RSA");

    StringBuilder ret = new StringBuilder();
    ret.append("partner=\"").append(sPara.get("partner")).append("\"")
            .append("&seller_id=\"").append(sPara.get("seller_id"))	.append("\"")
            .append("&out_trade_no=\"").append(sPara.get("out_trade_no")).append("\"")
            .append("&subject=\"").append(sPara.get("subject")).append("\"")
            .append("&body=\"").append(sPara.get("body")).append("\"")
            .append("&total_fee=\"").append(sPara.get("total_fee")).append("\"")
            .append("&notify_url=\"").append(sPara.get("notify_url")).append("\"")
            .append("&service=\"").append(sPara.get("service")).append("\"")
            .append("&_input_charset=\"").append("utf-8").append("\"")
            .append("&payment_type=\"").append(sPara.get("payment_type")).append("\"");
    logger.info("ret is : "+ret.toString());
    String mysign = RSA.sign(ret.toString(), privateKey, Global.ALI_PAY_INPUT_CHARSET);

    ret.append("&sign=\"").append(URLEncoder.encode(mysign,Global.ALI_PAY_INPUT_CHARSET)).append("\"")
            .append("&sign_type=\"").append("RSA").append("\"");
    return ret.toString();
  }

  public static String getFullUrlParam(Map<String, String> map){
    String sign = getSign(map, Global.ALI_PAY_PARTNER_PRIVATE_KEY);
    return getParasm(map)+"&sign="+sign;

  }

  private static String getParasm(Map<String, String> map){
    List<String> keys = new ArrayList<String>(map.keySet());
    // key排序
    Collections.sort(keys);

    StringBuilder authInfo = new StringBuilder();
    for (int i = 0; i < keys.size() - 1; i++) {
      String key = keys.get(i);
      String value = map.get(key);
      authInfo.append(buildKeyValue(key, value, false));
      authInfo.append("&");
    }

    String tailKey = keys.get(keys.size() - 1);
    String tailValue = map.get(tailKey);
    authInfo.append(buildKeyValue(tailKey, tailValue, false));
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
    String authInfo = getParasm(map);

    String oriSign = RSA.sign(authInfo, rsaKey, Global.ALI_PAY_INPUT_CHARSET);
    String encodedSign = "";

    try {
      encodedSign = URLEncoder.encode(oriSign, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return encodedSign;
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
        sb.append(URLEncoder.encode(value, "UTF-8"));
      } catch (UnsupportedEncodingException e) {
        sb.append(value);
      }
    } else {
      sb.append(value);
    }
    return sb.toString();
  }
  
}
