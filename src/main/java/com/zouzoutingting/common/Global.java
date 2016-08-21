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

	public static final String ALI_PAY_INPUT_CHARSET = "UTF-8";

	public static final String ALI_PAY_PAYMENT_TYPE = "1";

	public static final String ALI_PAY_INTERFACE_VERSION = "1.0";

	public static final String ALI_PAY_PARTNER_ID= "2088321043087101";//TODO 沙箱:2088102169325432 online:2088321043087101

	public static final String ALI_PAY_SELLER_ID = "2088321043087101";//TODO 沙箱:2088102169325432 online:2088321043087101

	private static final String ALI_APP_ID = "2016073100134649";//阿里appid  沙箱:2016073100134649  online:2016070801594335
	public static String
			ALI_PAY_PARTNER_PRIVATE_KEY="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALb1uL28eDYDyDG9\n" +
			"1xZMOdJB2ebtaPIw4pDyaoQw2jNxPOOS1/0aI2aykLvK8vVqAQMFzAG9hOXAZe8f\n" +
			"0NfLbKFUJ12zWjq3PkLkZQmKO9z6ZtluvxPX/Efz73OlBdZVEPURBOgmtT7UeoJJ\n" +
			"4m6cIDZW34rc5e2/CV4AHB0uVgotAgMBAAECgYACsamPuae3e+TFvrrdDDBJxUh1\n" +
			"GwfRkSC066zf12xPF5rC6xWdF3Zv+68f5MQhEveC2OIBII5FZ8jYdYp4svkdfJxk\n" +
			"mjrf6VrcojhzG/+jzReLshVcBpK3UzeSgo7tu9LrOEjTlyYDiQpRdgyZi9hdh+kR\n" +
			"Ft+MHfz1oaH4FxH+AQJBAPAAt8SNrIdK/E7QuvMOpPijnm0fd0hifGDXq3tN9fVm\n" +
			"TrJ+vWgLBZo30pmDLkN5hY2STuDTTnxPOdQv9jkuzIECQQDDJ6cYDpLmyrCtKua+\n" +
			"xUe61ur9GVkySfiw1QCiTnsZuTqcDtJpWSFWa2g61az+vjN/qMH4MJoUBEG59Lb/\n" +
			"NletAkBZ7DruZxKsglj0gtp49RS0oEFcq3x634OwmT4D7hrovlNgJ4J5+B86QA4l\n" +
			"PHRaDa8PczEgpatzgg+tw+aiWDmBAkEArHbVfzgZ8KX9nvuD8eGsc1zNTKFjJ0Mh\n" +
			"6TxEFCvhNClyt0mN/5XKFXJUKXJ+MJ5sKow75xckgz4Dy8+NDVu7/QJAHTt36zNA\n" +
			"1Maduqkj25skutRQ0ze/H66/XAS8+MNrCr7ePPdlOCtSU3qH7ancHIxmtx4Bb4l8\n" +
			"pfw99h9dA9CmZQ==";
	public static String ALI_PAY_PARTNER_PUB_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC29bi9vHg2A8gxvdcWTDnSQdnm7WjyMOKQ8mqEMNozcTzjktf9GiNmspC7yvL1agEDBcwBvYTlwGXvH9DXy2yhVCdds1o6tz5C5GUJijvc+mbZbr8T1/xH8+9zpQXWVRD1EQToJrU+1HqCSeJunCA2Vt+K3OXtvwleABwdLlYKLQIDAQAB";// TODO

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
