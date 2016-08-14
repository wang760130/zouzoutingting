package com.zouzoutingting.common;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月4日
 */
public class Global {
	
	/**
	 * 全局加密密钥
	 */
	public static final String DESKEY = "zouzoutingting";

	public static final String OFFLINE_DESKEY = "offlineUrl_zztt";
	
	public static final String RESULT_CONTENT = "resultcontent";
	
	public static final String DEFUALT_CHARSET = "utf-8";
	
	public static final String HOST_URL = "http://api2.imonl.com";
	
	public static final String TEST_HOST_URL = "http://api.test.imonl.com";

	public static final String WX_Trade_Type = "APP";//后续扩展,目前写死

	public static final String WX_APPID = "234ewfdfdfdsf";//TODO 微信appid

	public static final String WX_PAY_MCH_ID = "dsfs23234234";//TODO 微信支付商户id

	public static final String PAY_SUBJECT = "走走听听语音包";//TODO

	public static final String WX_PAY_INPUT_CHARSET = "UTF-8";

	public static final String WX_PAY_PARTER_KEY = "431a1ea3726c0dda18914e018bbc898a";//TODO 微信支付账号密码

	public static final String WX_PAY_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static final String Ali_PAY_SERVICE = "alipay.wap.create.direct.pay.by.user";

	public static final String ALI_PAY_INPUT_CHARSET = "UTF-8";

	public static final String ALI_PAY_PAYMENT_TYPE = "1";

	public static final String ALI_PAY_PARTNER_ID= "2088121206235635";//TODO

	public static String
			ALI_PAY_PARTNER_PRIVATE_KEY="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL3U6JcINvEKN3O+nTfw8oZ+vtCuqTbeReIYIT4iAgJxhd1MnvxbT7iMDwIjCPoN9NC0ccDUBaCviZNiviJasKyAujjR0HxQPD8utJp7Q/dM8X/z2G9Wt79fxXVT6yTG8LQP3FynDiOEcd5Hok2sROw3msYm5rBaDtznXjEXvv7LAgMBAAECgYBpQgvx6vEj+ElFGZVtdmHT56WAasiTDu5q7nxISm0Y07+pNJoXTb5HkwmjYq/QjzqHGvbUN/wi3BKJsb/Bps289DN73eMlP8razOsh4Qt95gbFOYCq6C0LPHN+pLttyEA4Q3pqjZURz7xBRYYoYR1gGyXbO+FOZ7QSqcDk4U8mIQJBAOZKxYmJsXCdt6rnDzBvimN3PxCCOYsHHmEOZ9Gt49AyYayUI6qUHCLC4E9QtvnlIUgJmW/XWpimH7qNWuFE5nECQQDTBd6tYg4GVF1nHff6UctryXv4epfv/8KJ6FZbbdoelOUauSSeq8+8fX7VpCzarAlnuRZw/SEYg67BcUUcNO77AkEAlivMBJQ1kSpHyrpBvWP+6j2ocit5Op+5v7CVIrYyCGHSL6eqWmGat1A81Xvc1bgEq+UWmUflXAV4Sz5CNQOpAQJAEd4EXa/YuiZ4hhuefH5id+zmZ0KSDMseAKlAbptdhYtb3qhgdmLbwvzpOVOeTKGWZkbgpI56N+YmbNCXZm0A8wJAbbiAXQxVXzGN3NNcVptsrw9pAf91o68vMcBLQKjEYQlE9xfG/OL1fAXAanJz6Yae9l6jzgPG1QS5jHJlPIhKAg==";
	public static String ALI_PAY_PARTNER_PUB_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh" +
			"/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB" +
			"/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";// TODO

	//支付宝提供给商户的服务接入网关URL
	public static final String ALI_PAY_GATEWAY_URL = "https://mapi.alipay.com/gateway.do?";
	public static final String ALI_PAY_SELLER_MAIL = "zouzoutingting@aliyun.com";//TODO
}
