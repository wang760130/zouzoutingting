package com.zouzoutingting.common;

import com.zouzoutingting.utils.PropManager;

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
	
	public static final String HOST_URL = "http://api.imonl.com";
	
	public static final String TEST_HOST_URL = "http://api.test.imonl.com";

	public static final String WX_Trade_Type = "APP";//后续扩展,目前写死

	public static final String WX_APPID = "wx01aca992eaa691c2";//TODO 微信appid

	public static final String WX_PAY_MCH_ID = "1365688602";//TODO 微信支付商户id

	public static final String PAY_SUBJECT = "走走听听语音包";//TODO

	public static final String WX_PAY_INPUT_CHARSET = "UTF-8";

	public static final String WX_PAY_PARTER_KEY = "931a1ea3726c0dda18914e018bbc898c";//TODO 微信支付密钥

	public static final String WX_PAY_UNIFIED_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static final String Ali_PAY_SERVICE = "alipay.wap.create.direct.pay.by.user";

	public static final String ALI_PAY_INPUT_CHARSET = "utf-8";

	public static final String ALI_PAY_PAYMENT_TYPE = "1";

	public static final String ALI_PAY_INTERFACE_VERSION = "1.0";

	public static final String ALI_PAY_PARTNER_ID= "2088321043087101";//TODO 沙箱:2088102169325432 online:2088321043087101

	public static final String ALI_PAY_SELLER_ID = "2088321043087101";//TODO 沙箱:2088102169325432 online:2088321043087101

	private static final String ALI_APP_ID = "2016070801594335";//阿里appid  沙箱:2016073100134649  online:2016070801594335

//	public static String ALI_PAY_PARTNER_PRIVATE_KEY="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL3U6JcINvEKN3O+nTfw8oZ+vtCuqTbeReIYIT4iAgJxhd1MnvxbT7iMDwIjCPoN9NC0ccDUBaCviZNiviJasKyAujjR0HxQPD8utJp7Q/dM8X/z2G9Wt79fxXVT6yTG8LQP3FynDiOEcd5Hok2sROw3msYm5rBaDtznXjEXvv7LAgMBAAECgYBpQgvx6vEj+ElFGZVtdmHT56WAasiTDu5q7nxISm0Y07+pNJoXTb5HkwmjYq/QjzqHGvbUN/wi3BKJsb/Bps289DN73eMlP8razOsh4Qt95gbFOYCq6C0LPHN+pLttyEA4Q3pqjZURz7xBRYYoYR1gGyXbO+FOZ7QSqcDk4U8mIQJBAOZKxYmJsXCdt6rnDzBvimN3PxCCOYsHHmEOZ9Gt49AyYayUI6qUHCLC4E9QtvnlIUgJmW/XWpimH7qNWuFE5nECQQDTBd6tYg4GVF1nHff6UctryXv4epfv/8KJ6FZbbdoelOUauSSeq8+8fX7VpCzarAlnuRZw/SEYg67BcUUcNO77AkEAlivMBJQ1kSpHyrpBvWP+6j2ocit5Op+5v7CVIrYyCGHSL6eqWmGat1A81Xvc1bgEq+UWmUflXAV4Sz5CNQOpAQJAEd4EXa/YuiZ4hhuefH5id+zmZ0KSDMseAKlAbptdhYtb3qhgdmLbwvzpOVOeTKGWZkbgpI56N+YmbNCXZm0A8wJAbbiAXQxVXzGN3NNcVptsrw9pAf91o68vMcBLQKjEYQlE9xfG/OL1fAXAanJz6Yae9l6jzgPG1QS5jHJlPIhKAg==";
//	public static String ALI_PAY_PARTNER_PUB_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	public static final String ALI_PAY_PARTNER_PUB_KEY =
			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";//支付宝公钥,非本地公钥
	//	public static final String ALI_PAY_self_PUB_KEY =
//		"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsgOlMiAFMy2JjzG6qs8QenL7EU4BbulCiy1VwdlVFE0s2H8qozMZVmUKQ1ymeJZKM22O3aQnZvHf9LzW5HezmLmfDQ+38TKLCe7s6LpVGtAqlTEN87DD8whTwaNmMnqvpVhpqBwoxlOI0NU8bgsTMjMSthgx0lg4I+FZ+YWWqRwIDAQAB";
	public static final String ALI_PAY_PARTNER_PRIVATE_KEY =
			"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKyA6UyIAUzLYmPM\n" +
			"bqqzxB6cvsRTgFu6UKLLVXB2VUUTSzYfyqjMxlWZQpDXKZ4lkozbY7dpCdm8d/0v\n" +
			"Nbkd7OYuZ8ND7fxMosJ7uzoulUa0CqVMQ3zsMPzCFPBo2Yyeq+lWGmoHCjGU4jQ1\n" +
			"TxuCxMyMxK2GDHSWDgj4Vn5hZapHAgMBAAECgYBP2AMkFzdy0hiiVLdMb5RxDuAF\n" +
			"gxjAXEaCc1cCjOL+6+U+Egz9gHq0mBGFdPGgb4ebhthqxkV28JMhLz7Qhhdl2GiT\n" +
			"YtozZSmDZn0ICtpQprRyCx6MpxTdqxdo/8yDJMBLvsZ5wOfStwA758KgBSFoOo/F\n" +
			"P1Lu+jgy9nFr98sgsQJBAN7ztHdnUxnTBCFfgA+Bauob4Bmy8PejP8/FzLMCTSJg\n" +
			"CWj08cIuAfEokLrHV0gDznqH7B47f1NHBWqUuFrRaOUCQQDGEtrVhqAFle3ZiTpI\n" +
			"YHmyoXnns87u65PdL0nc61NkTK5CkV8GCnzaVjcRBEy/NM2P3amHnUGCYiuVr8tQ\n" +
			"vC+7AkEAhT1jtpAbS62eJEusfpe7S1mJXhJgi74WHpd5M+nGKyr1tLHd4UwZgQPb\n" +
			"KqSC9ti/ht7AL3kjKKOjCwUB/9RyOQJAYrxxY7FZQY+MYj0a1Ytp/tapqwuh4s2C\n" +
			"BucUvdB84GmtEDfhE2TU+g2yI4RuMX5D3ixT3utQBYukSFi6VAOLEwJAaCtCL7rn\n" +
			"iq3RkoQGpmrblXJpJa+qkuVOuOaOmh/mfpY1T0wg9V1701SSzyTRTiOUka71snqZ\n" +
			"2+s9/hxMwmx/cA==";

	//支付宝提供给商户的服务接入网关URL
	private static final String ALI_PAY_GATEWAY_URL = "https://mapi.alipay.com/gateway.do?";
	public static final String ALI_PAY_SELLER_MAIL = "zouzoutingting@aliyun.com";//TODO
	public static String getAliPayParterID(){
		String aliparterid = PropManager.getSingletonInstance().getProperty("ALI_PAY_PARTNER_ID");
		if(aliparterid==null){
			aliparterid = ALI_PAY_PARTNER_ID;
		}
		return aliparterid;
	}
	public static String getAliPaySellerId(){
		String alipaySellid = PropManager.getSingletonInstance().getProperty("ALI_PAY_SELLER_ID");
		if(alipaySellid==null){
			alipaySellid = ALI_PAY_SELLER_ID;
		}
		return alipaySellid;
	}

	public static String getAliPayGatewayUrl(){
		String ali_pay_gateway_url = PropManager.getSingletonInstance().getProperty("ALI_PAY_GATEWAY_URL");
		if(ali_pay_gateway_url==null){
			ali_pay_gateway_url = ALI_PAY_GATEWAY_URL;
		}
		return ali_pay_gateway_url;
	}


	public static String getAliAppID(){
		String appid = PropManager.getSingletonInstance().getProperty("ALI_APP_ID");
		if(appid==null){
			appid = ALI_APP_ID;
		}
		return appid;
	}
}
