package com.zouzoutingting.utils.alipay;

import com.zouzoutingting.common.Global;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

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
    StringBuilder ret = new StringBuilder();
    // 除去数组中的空值和签名参数
    Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
    ret.append("_input_charset=\"").append(sPara.get("_input_charset")).append("\"")
        .append("&body=\"").append(sPara.get("body")).append("\"")
        .append("&notify_url=\"").append(sPara.get("notify_url")).append("\"")
        .append("&out_trade_no=\"").append(sPara.get("out_trade_no")).append("\"")
        .append("&partner=\"").append(sPara.get("partner")).append("\"")
        .append("&payment_type=\"").append(sPara.get("payment_type")).append("\"");
    ret.append("&seller_id=\"").append(sPara.get("seller_id")).append("\"")
        .append("&service=\"").append(sPara.get("service")).append("\"");
    ret.append("&subject=\"").append(sPara.get("subject")).append("\"")
        .append("&total_fee=\"").append(sPara.get("total_fee")).append("\"");
    logger.info("APP加密前串：" + ret);
      String mysign = RSA.sign(ret.toString(), privateKey, Global.ALI_PAY_INPUT_CHARSET);
      ret.append("&sign=\"").append(URLEncoder.encode(mysign, Global.ALI_PAY_INPUT_CHARSET))
      .append("\"").append("&sign_type=\"").append("RSA").append("\"");
      return ret.toString();
  }
  
}
