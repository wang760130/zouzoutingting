package com.zouzoutingting.utils.alipay;

import com.zouzoutingting.common.Global;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
//	/**
//	 * 发起支付宝退款
//	 * 变更支付状态
//	 * @param payLog
//	 * @param record
//	 * @param dto
//	 */
//	public static void doRefund(PayLog payLog, RefundLog record,
//			RefundResultDto dto) {
//		// 发起支付宝支付
//		String reqURL = null;
//		boolean isNew = false;
//		try {
//          String changeTime = PropManager.getSingletonInstance().getProperty("paybody_change_time");
//          if(StringUtils.isNotEmpty(changeTime)){
//            LOGGER.info("ali changer time:{}",changeTime);
//            if(payLog.getCreateTime().getTime() > DateFormatUtil.YYYY_MM_DD_HH_MM_SS.parseMillis(changeTime)){
//              LOGGER.info("支付宝新主体退款");
//              isNew = true;
//            }
//          }
//			reqURL = AlipayCore.getRefundReqURL(payLog.getTradeNo(),DoubleUtil.divBy1000(record.getTotalFee()),isNew);
//			String httpResult = HttpClientUtil.httpClientGet(reqURL);
//			LOGGER.info("通知支付宝退款完成， 支付URL: " + reqURL + ",支付结果: " + httpResult);
//			Map<String, String> map = null;
//			map = XMLUtil.doXMLParse(httpResult);
//			// 支付成功构造退款记录
//			if (map != null && map.get("is_success").equals("T")) {
//				record.setStatus(RefundStatusEnum.RENFUND_SUCCESS);
//				dto.setResult(CommonConstant.RESULT_SUCCESS);
//				dto.setRefundId(record.getId());
//				dto.setMoney(record.getTotalFee());
//				LOGGER.info("支付宝退款成功,tradeId: " + record.getTradeId()
//						+ ",退款金额: " + record.getTotalFee());
//			} else {
//				record.setStatus(RefundStatusEnum.RENFUND_FAIL);
//				record.setFailReason(map == null ? "" : map.get("error"));
//				dto.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
//				dto.setErrMsg(ErrorCodeEnum.SYSTEM_ERROR.getDesc());
//				dto.setResult(CommonConstant.RESULT_FAIL);
//				LOGGER.error("支付宝退款失败,tradeId: " + record.getTradeId()
//						+ ",退款金额: " + record.getTotalFee() + ",原因:"
//						+ httpResult);
//			}
//		} catch (UnsupportedEncodingException e) {
//			record.setStatus(RefundStatusEnum.RENFUND_FAIL);
//			record.setFailReason(ErrorCodeEnum.SYSTEM_ERROR.getDesc());
//			dto.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
//			dto.setErrMsg(ErrorCodeEnum.SYSTEM_ERROR.getDesc());
//			dto.setResult(CommonConstant.RESULT_FAIL);
//			LOGGER.error("支付宝退款失败,tradeId: " + record.getTradeId()
//					+ ",退款金额: " + record.getTotalFee(), e);
//		} catch (JDOMException e) {
//			record.setStatus(RefundStatusEnum.RENFUND_FAIL);
//			record.setFailReason(ErrorCodeEnum.SYSTEM_ERROR.getDesc());
//			dto.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
//			dto.setErrMsg(ErrorCodeEnum.SYSTEM_ERROR.getDesc());
//			dto.setResult(CommonConstant.RESULT_FAIL);
//			LOGGER.error("支付宝退款失败,tradeId: " + record.getTradeId()
//					+ ",退款金额: " + record.getTotalFee(), e);
//		} catch (IOException e) {
//			record.setStatus(RefundStatusEnum.RENFUND_FAIL);
//			record.setFailReason(ErrorCodeEnum.SYSTEM_ERROR.getDesc());
//			dto.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
//			dto.setErrMsg(ErrorCodeEnum.SYSTEM_ERROR.getDesc());
//			dto.setResult(CommonConstant.RESULT_FAIL);
//			LOGGER.error("支付宝退款失败,tradeId: " + record.getTradeId()
//					+ ",退款金额: " + record.getTotalFee(), e);
//		}
//	}
	
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

	/**
	 * 把数组所有元素按照固定参数排序，以“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkStringNoSort(Map<String, String> params) {

		// 手机网站支付MD5签名固定参数排序，顺序参照文档说明
		StringBuilder gotoSign_params = new StringBuilder();
		gotoSign_params.append("service=" + params.get("service"));
		gotoSign_params.append("&v=" + params.get("v"));
		gotoSign_params.append("&sec_id=" + params.get("sec_id"));
		gotoSign_params.append("&notify_data=" + params.get("notify_data"));

		return gotoSign_params.toString();
	}

	/**
	 * 生成签名结果
	 * 
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildAliPayReqSign(final Map<String, String> sPara) {
		// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = AlipayCore.createLinkString(sPara);
		String sign = RSA.sign(prestr, Global.ALI_PAY_PARTNER_PRIVATE_KEY,
				Global.ALI_PAY_INPUT_CHARSET);
		return sign;
	}

	/**
	 * 生成要请求给支付宝的参数数组
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @return 要请求的参数数组
	 */
	public static Map<String, String> buildRequestPara(
			final Map<String, String> sParaTemp) {
		// 除去数组中的空值和签名参数
		Map<String, String> param = AlipayCore.paraFilter(sParaTemp);
		// 生成签名结果
		String sign = buildAliPayReqSign(param);
		// 签名结果与签名方式加入请求提交参数组中
		param.put("sign", sign);
		param.put("sign_type", "RSA");
		return param;
	}

	/**
	 * 生成要请求给支付宝的参数
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @return 要请求的参数数组
	 * @throws UnsupportedEncodingException
	 */
	public static String buildRequestURL(Map<String, String> sParaTemp)
			throws UnsupportedEncodingException {
		StringBuilder ret = new StringBuilder();
		// 除去数组中的空值和签名参数
		Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		// 生成签名结果
		String mysign = buildAliPayReqSign(sPara);
		// 签名结果与签名方式加入请求提交参数组中
		sPara.put("sign", mysign);
		sPara.put("sign_type", "RSA");

		List<String> keys = new ArrayList<String>(sPara.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String name = keys.get(i);
			String value = sPara.get(name);
			ret.append(name)
					.append("=")
					.append(URLEncoder.encode(value,
							Global.ALI_PAY_INPUT_CHARSET));
			if (i < keys.size() - 1)
				ret.append("&");
		}
		return Global.getAliPayGatewayUrl() + ret.toString();
	}
	
	
//	/**
//     * (新)生成要请求给支付宝的参数
//     *
//     * @param sParaTemp
//     *            请求前的参数数组
//     * @return 要请求的参数数组
//     * @throws UnsupportedEncodingException
//     */
//    public static String buildNewRequestURL(Map<String, String> sParaTemp)
//            throws UnsupportedEncodingException {
//        StringBuilder ret = new StringBuilder();
//        // 除去数组中的空值和签名参数
//        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
//        // 生成签名结果
//        String prestr = AlipayCore.createLinkString(sPara);
//        String mysign = RSA.sign(prestr, Global.ALI_PAY_PARTNER_PRIVATE_KEY,
//                Global.ALI_PAY_INPUT_CHARSET);
//        // 签名结果与签名方式加入请求提交参数组中
//        sPara.put("sign", mysign);
//        sPara.put("sign_type", "RSA");
//
//        List<String> keys = new ArrayList<String>(sPara.keySet());
//        for (int i = 0; i < keys.size(); i++) {
//            String name = keys.get(i);
//            String value = sPara.get(name);
//            ret.append(name)
//                    .append("=")
//                    .append(URLEncoder.encode(value,
//                            Global.ALI_PAY_INPUT_CHARSET));
//            if (i < keys.size() - 1)
//                ret.append("&");
//        }
//        return Global.ALI_PAY_GATEWAY_URL + ret.toString();
//    }

//	/**
//	 * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
//	 *
//	 * @return 时间戳字符串
//	 * @throws IOException
//	 * @throws DocumentException
//	 * @throws MalformedURLException
//	 */
//	public static String query_timestamp() throws MalformedURLException,
//			DocumentException, IOException {
//		// 构造访问query_timestamp接口的URL串
//		String strUrl = Global.ALI_PAY_GATEWAY_URL
//				+ "service=query_timestamp&partner="
//				+ Global.ALI_PAY_PARTNER_ID + "&_input_charset"
//				+ Global.ALI_PAY_INPUT_CHARSET;
//		StringBuffer result = new StringBuffer();
//
//		SAXReader reader = new SAXReader();
//		Document doc = reader.read(new URL(strUrl).openStream());
//		List<Node> nodeList = doc.selectNodes("//alipay/*");
//		if (nodeList != null && nodeList.size() > 0) {
//			for (Node node : nodeList) {
//				// 截取部分不需要解析的信息
//				if (node.getName().equals("is_success")
//						&& node.getText().equals("T")) {
//					// 判断是否有成功标示
//					List<Node> nodeList1 = doc
//							.selectNodes("//response/timestamp/*");
//					for (Node node1 : nodeList1) {
//						result.append(node1.getText());
//					}
//				}
//			}
//		}
//		return result.toString();
//	}

	
//	/**
//	 * 构造支付宝退款url
//	 *
//	 * @param tradeNo
//	 *            支付宝支付流水id
//	 * @param money
//	 *            退款金额
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	public static String getRefundReqURL(String tradeNo, double money)
//			throws UnsupportedEncodingException {
//		Map<String, String> params = new HashMap<String, String>();
//		String resultUrl = "";
//		params.put("_input_charset", "UTF-8");
//		params.put("batch_num", "1");
//		params.put("refund_date",
//				YYYY_MM_DD_HH_MM_SS.print(new Date().getTime()));
//		params.put("service", "refund_fastpay_by_platform_nopwd");
//		// 退款
//		params.put("detail_data", tradeNo + "^" + money + "^组合支付账户余额不足导致支付失败");
//		params.put("sign_type", "RSA");
//		params.put("batch_no", getAlipayBatchNo());
//		params.put("seller_email", Global.ALI_PAY_SELLER_MAIL);
//		params.put("partner", Global.ALI_PAY_PARTNER_ID);
//		resultUrl = buildNewRequestURL(params);
//		return resultUrl;
//	}

//	public static String getAlipayBatchNo() {
//		String batchNo = "";
//		try {
//
//			batchNo = YYYYMMDD.print(new Date().getTime()) + "" + SimpleNumberUuidProvider.uuid();
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return batchNo;
//	}
}
