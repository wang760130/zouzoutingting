package com.zouzoutingting.service.impl;

import com.alibaba.fastjson.JSON;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.components.dao.Page;
import com.zouzoutingting.enums.CouponStateEnum;
import com.zouzoutingting.enums.OrderStateEnum;
import com.zouzoutingting.model.Coupon;
import com.zouzoutingting.model.Order;
import com.zouzoutingting.model.PrePayResult;
import com.zouzoutingting.service.IPayService;
import com.zouzoutingting.utils.*;
import com.zouzoutingting.utils.alipay.AliParamCore;
import com.zouzoutingting.utils.alipay.AlipayCore;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangyong on 16/6/19.
 */
@Service("payService")
public class PayServiceImpl implements IPayService{
    private static final Logger logger = Logger.getLogger(PayServiceImpl.class);
    private static SimpleDateFormat sdf_Ali = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf_Wx = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    private IDao<Order> orderDao;

    @Autowired
    private IDao<Coupon> couponDao;

    @Override
    public PrePayResult getWxPreyPayInfo(Order order) throws Exception{
        PrePayResult resultDto = new PrePayResult();
        resultDto.setOrderid(order.getOrderid());
        resultDto.setResult(false);
        Map<String, String> resultMap = getWxPreyPayInfoFromWx(order);
        String respresut = String.valueOf(resultMap.get("result_code"));
        //拼接调起支付参数
        if((respresut).equals("SUCCESS")){

            resultDto.setParamMap(AppBuildWxRequestStr(resultMap, order));
            resultDto.setResult(true);
            logger.info("wechat jointPayParams success,orderid={}"+order.getOrderid() +",result={}" + JSON.toJSON(resultDto));
        }else{
            resultDto.setErrMsg(String.valueOf(resultMap.get("return_msg")));
            if(resultMap.get("err_code_des") != null){
                resultDto.setErrMsg(resultDto.getErrMsg() + String.valueOf(resultMap.get("err_code_des")));
            }
        }
        return resultDto;
    }

    @Override
    public boolean wxPayNotify(BufferedReader br, Map<String, String> params) {
        boolean ret = false;
        try{
            Map<String, String> requestMap = wxPayParamsNotify(br);
            if(requestMap==null){
                logger.info("wxPayParamsNotify WXNotify 返回结果：---------" + ret +" params:" + JSON.toJSONString(params));
            }else {
                ret = wxPayLogicNotify(params, requestMap);
            }
        }catch (Exception e){
            logger.error("wx notify Error"+" params:" + JSON.toJSONString(params), e);
        }
        return ret;
    }

    /**
     * 通过微信返回结果判断合法性
     * @param br
     * @return
     * @throws Exception
     */
    private Map<String, String> wxPayParamsNotify(BufferedReader br) throws Exception{
        String requestResult = "";
        String line = "";
        for (line = br.readLine(); line != null; line = br.readLine()) {
            requestResult = requestResult + line;
        }
        logger.info("PayServiceImpl wxPayParamsNotify:得到微信回调内容---------" + requestResult);
        Map<String, String> requestMap = XMLUtil.doXMLParse(requestResult);
        if (requestMap.get("return_code").equals("SUCCESS")) {
            return requestMap;
        } else {
            logger.info("PayServiceImpl wxPayParamsNotify:通知签名验证失败");
            return null;
        }
    }

    private boolean wxPayLogicNotify(Map<String, String> params, Map<String, String> requestMap){
        boolean result = false;
        String orderidStr =requestMap.get("out_trade_no");
        if(StringUtils.isNotBlank(orderidStr) && orderidStr.equals(params.get("orderid"))){
            String transaction_id = requestMap.get("transaction_id");//微信支付订单号
            String paytime = requestMap.get("time_end");
            Order order = orderDao.load(Long.parseLong(orderidStr));
            if(order!=null){
                if(order.getState()!= OrderStateEnum.Finish.getState()){
                    logger.info("wx orderid:"+orderidStr+" payed state "+order.getState()+"-->"+OrderStateEnum.Finish
                            .getState());
                    order.setState(OrderStateEnum.Finish.getState());
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("paytype","wx");
                    map.put("trade_no",transaction_id);
                    order.setComment(JSON.toJSONString(map));
                    order.setPayTime(getPayDate(paytime, sdf_Wx));
                    order.setUpdateTime(new Date());
                    orderDao.save(order);
                    //处理券状态
                    dealCouponUse(orderidStr, params, "wx");
                    logger.info("order:"+JSON.toJSONString(order)+" wx 支付 ok");
                    //TODO纪录日志
                }
                result = true;
            }else{
                logger.error("orderID:"+orderidStr+" wxNotify get order null!!");
            }
        }else{
            logger.error("orderID:"+orderidStr+" wxNotify param Wrong!!");
        }
        return result;
    }

    private void dealCouponUse(String orderidStr, Map<String,String> params, String payType){
        //处理券状态
        String couponCode = params.get("couponcode");
        if(StringUtils.isNotBlank(couponCode)&& !couponCode.equals("null")){
            Coupon coupon = getCouponByCode(couponCode.trim().toUpperCase());
            if(coupon.getState()== CouponStateEnum.Used.getState()){
                logger.error("!!!!!!!券已使用,多次使用!!!! code:"+couponCode+", orderid:"+orderidStr);
            }else{
                coupon.setState(CouponStateEnum.Used.getState());
                coupon.setUpdatetime(new Date());
                Map<String,String> coupmap = new HashMap<String, String>();
                coupmap.put("orderid", orderidStr);
                coupmap.put("paytype", payType);
                coupon.setComment(JSON.toJSONString(coupmap));
                couponDao.save(coupon);
                logger.info(payType + " orderid:"+orderidStr+" 使用 券:"+couponCode+" ok");
            }
        }
    }

    private Coupon getCouponByCode(String code) {
        Coupon coupon = null;
        String condition = "code = '" + code+"'";
        Page page = new Page();
        page.setCondition(condition);
        page.setPageNo(1);
        page.setPageSize(1);
        List<Coupon> list = couponDao.page(page);
        if(list!=null && list.size()>0){
            coupon = list.get(0);
        }
        return coupon;
    }

    private Date getPayDate(String paytime, SimpleDateFormat sdf){
        Date payDate = null;
        try {
            if (paytime != null) {
                payDate = sdf.parse(paytime);
            } else {
                payDate = new Date();
            }
        }catch (Exception e){
            e.printStackTrace();
            payDate = new Date();
        }
        return payDate;
    }

    @Override
    public PrePayResult getAliPreyPayInfo(Order order) throws Exception {
        PrePayResult resultDto = new PrePayResult();
        resultDto.setOrderid(order.getOrderid());
        resultDto.setResult(false);

        Map<String, String> sParaTemp = new HashMap<String, String>();
        //notifyurl
        String extInfo = order.getOrderid()  + "_" + order.getUid() + "_" + order.getVid() + "_" + order.getCode();
        String notifyUrl = PropManager.getSingletonInstance().getProperty("ali_notify_url");
        String code = order.getCode();
        if(StringUtils.isEmpty(code)){
            code = "null";
        }
        notifyUrl = notifyUrl + code+"/"+order.getOrderid()+"/" + order.getUid()+"/" + order.getVid();
        sParaTemp.put("app_id", Global.getAliAppID());
        sParaTemp.put("method", "alipay.trade.app.pay");
        sParaTemp.put("charset", Global.ALI_PAY_INPUT_CHARSET);
        sParaTemp.put("sign_type", "RSA");
        sParaTemp.put("timestamp", sdf_Ali.format(new Date()));
        sParaTemp.put("version", Global.ALI_PAY_INTERFACE_VERSION);
        sParaTemp.put("notify_url", notifyUrl);

        Map<String, String> bizMap = new HashMap<String, String>();
        bizMap.put("body",Global.PAY_SUBJECT);
        bizMap.put("subject", Global.PAY_SUBJECT);
        bizMap.put("out_trade_no", order.getOrderid()+"");
        bizMap.put("total_amount", order.getNeedpay() + "");
        bizMap.put("seller_id", Global.getAliPaySellerId());
        bizMap.put("product_code", "QUICK_MSECURITY_PAY");
        sParaTemp.put("biz_content",JSON.toJSONString(bizMap));

        String params = null;
        try {
            params = AliParamCore.getFullUrlParam(sParaTemp);
            resultDto.setParamMap(params);
            logger.info("ali jointPayParams success,orderid=" + order.getOrderid() +" data:"+params);
        } catch (Exception e) {
            logger.error("ali buildRequestStr err,date=" + JSON.toJSONString(sParaTemp),e);
            resultDto.setErrMsg("加签失败");
        }
        try{
            String[] pair = params.split("&");
            Map<String, String> postMap = new HashMap<String, String>();
            for(String pp : pair){
                String[] kv = pp.split("=");
                if(kv.length==2) {
                    postMap.put(kv[0], kv[1]);
                }
            }
            String url = Global.getAliPayGatewayUrl();
            String body = HttpUtil.post(url, postMap);
            if(StringUtils.isNotBlank(body)){
                logger.info("ali prepay http "+url+" ---invoke result:"+body);
                resultDto.setResult(true);
            }else {
                logger.info("ali prepay: "+ url+" return null");
            }
        }catch (Exception e){
            logger.error("向支付宝创建订单失败", e);
            resultDto.setErrMsg("向支付宝创建订单失败");
        }
        return resultDto;
    }

    @Override
    public boolean AliPayNotify(Map requestMap, Map params) {
        boolean ret = false;

        Map<String,String> tparams = null;
        try {
            tparams = aLiPayParamsNotify(requestMap);
            if(params.isEmpty()){
                logger.info(" aLiPayNotify aLiPayParamsNotify 失败！！！");
            }else {
                ret = aLiPayLogicNotify(tparams, params);
            }

        } catch (Exception e) {
            logger.error("阿里支付回调 Error" ,e);
        }
        return ret;
    }

    /**
     * 组织入参
     * @param requestParams
     * @return
     * @throws Exception
     */
    private Map<String,String> aLiPayParamsNotify(Map requestParams) throws  Exception {
        Map<String,String> params = new HashMap<String,String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
            logger.info(" aLiPayParamsNotify name is :"+name+"; value is :"+valueStr);
        }
        return params;
    }

    /**
     * 阿里回调逻辑处理
     * @param requestParams 整体参数
     * @param params 回到url中传入参数
     * @return
     * @throws Exception
     */
    private boolean aLiPayLogicNotify(Map requestParams, Map params) throws Exception{
        boolean result = false;

        if(verify(params)) {//验证成功
            String orderidStr = (String) requestParams.get("out_trade_no");
            String paytime = (String) requestParams.get("gmt_payment");
            if (StringUtils.isNotBlank(orderidStr) && orderidStr.equals(params.get("orderid"))) {
                Order order = orderDao.load(Long.parseLong(orderidStr));
                if (order != null) {
                    String trade_no = (String) requestParams.get("trade_no");//支付宝交易号
                    String trade_status = (String) requestParams.get("trade_status");
                    if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                        if (order.getState() != OrderStateEnum.Finish.getState()) {
                            logger.info("ali orderid:" + orderidStr + " payed state " + order.getState() + "-->" + OrderStateEnum.Finish
                                    .getState());
                            order.setState(OrderStateEnum.Finish.getState());
                            //todo 纪录日志
                            Map<String,String> map = new HashMap<String, String>();
                            map.put("paytype","ali");
                            map.put("trade_no",trade_no);
                            order.setComment(JSON.toJSONString(map));
                            order.setPayTime(getPayDate(paytime, sdf_Ali));
                            orderDao.save(order);
                            dealCouponUse(orderidStr, params, "ali");
                            logger.info("order:"+JSON.toJSONString(order)+" ali 支付 ok");
                        }
                        result = true;
                    }else{
                        logger.info("回调状态为未完成 oid:"+orderidStr+","+JSON.toJSONString(requestParams));
                    }

                } else {
                    logger.error("orderID:" + orderidStr + " aliNotify get order null!!");
                }
            } else {
                logger.error("orderID:" + orderidStr + " aliNotify param Wrong!!");
            }
        }else {
            logger.error("AliNotify verfy wrong:" + JSON.toJSONString(requestParams));
        }
        return result;
    }

    /**
     * 获取远程服务器ATN结果,验证返回URL
     * @param notify_id 通知校验ID
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private String verifyResponse(String notify_id) {
        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

        String partner = Global.getAliPayParterID();
        String veryfy_url = Global.getAliPayGatewayUrl() + "service=notify_verify&partner=" + partner + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }

    /**
     * 获取远程服务器ATN结果
     * @param urlvalue 指定URL路径地址
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private String checkUrl(String urlvalue) {
        String inputLine = "";

        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                    .getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }

        return inputLine;
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
    private static boolean getSignVeryfy(Map<String, String> Params, String sign) {
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
        //获取待签名字符串
        String preSignStr = AlipayCore.createLinkString(sParaNew);
        logger.info(" AlipayNotify getSignVeryfy preSignStr" +preSignStr);
        //获得签名验证结果
        boolean isSign = false;
        logger.info(" AlipayNotify getSignVeryfy preSignStr" +preSignStr);
        logger.info(" AlipayNotify getSignVeryfy sign" +sign);
        logger.info(" AlipayNotify getSignVeryfy alipay_pubkey" +Global.ALI_PAY_PARTNER_PUB_KEY);
        isSign = com.zouzoutingting.utils.alipay.RSA.verify(preSignStr, sign, Global.ALI_PAY_PARTNER_PUB_KEY, Global
                .ALI_PAY_INPUT_CHARSET);
        logger.info(" AlipayNotify getSignVeryfy isSign" +isSign);
        return isSign;
    }


    /**
     * 验证消息是否是支付宝发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public boolean verify(Map<String, String> params) {

        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        String responseTxt = "true";
        if(params.get("notify_id") != null) {
            String notify_id = params.get("notify_id");
            responseTxt = verifyResponse(notify_id);
        }
        logger.info(" AlipayNotify verify responseTxt" +responseTxt);
        String sign = "";
        if(params.get("sign") != null) {sign = params.get("sign");}
        logger.info(" AlipayNotify verify sign" +sign);
        boolean isSign = getSignVeryfy(params, sign);
        logger.info(" AlipayNotify verify isSign" +isSign);
        //写日志记录（若要调试，请取消下面两行注释）
        String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
	    logger.info("AliPayNotify verfy:" + sWord);

        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证支付宝返回参数的合法性
     * @param params
     * @return
     * @throws Exception
     */
    private String aLiPayVerify(Map<String,String> params) throws  Exception {
        String ret="fail";
        //交易状态
        String trade_status = params.get("trade_status");
        String out_trade_no=params.get("out_trade_no");
        if(verify(params)){//验证成功
            logger.info(" aLiPayVerify 签名验证成功 out_trade_no:"+out_trade_no +" trade_status: "+trade_status);
            if (trade_status.equals("TRADE_SUCCESS")){
                logger.info(" aLiPayVerify 支付验证成功 out_trade_no:"+out_trade_no +" trade_status: "+trade_status);
                ret="success";
            }else if (trade_status.equals("TRADE_FINISHED")){
                ret="finished";
                logger.info(" aLiPayVerify 支付验证完成 out_trade_no:"+out_trade_no+" trade_status: "+trade_status);
            }else{
                logger.info(" aLiPayVerify 支付验证失败 out_trade_no:"+out_trade_no+" trade_status: "+trade_status);
            }
        }else{//验证失败
            ret="fail";
            logger.info(" aLiPayVerify 验证失败 out_trade_no:"+out_trade_no+" trade_status: "+trade_status);
        }
        logger.info(" aLiPayVerify ret:"+ret+"!!! out_trade_no:"+out_trade_no+" trade_status: "+trade_status);
        return ret;
    }

    private Map<String, String> getWxPreyPayInfoFromWx(Order order) throws Exception{
        SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
        //notifyurl
        String extInfo = order.getOrderid()  + "_" + order.getUid() + "_" + order.getVid() + "_" + order.getCode();
        String notifyUrl = PropManager.getSingletonInstance().getProperty("wx_notify_url");
        String code = order.getCode();
        if(StringUtils.isEmpty(code)){
            code = "null";
        }
        notifyUrl = notifyUrl + code+"/"+order.getOrderid()+"/" + order.getUid()+"/" + order.getVid();

        sParaTemp.put("trade_type", Global.WX_Trade_Type);
        sParaTemp.put("nonce_str", WXUtil.getNonceStr());
        sParaTemp.put("notify_url", notifyUrl);
        sParaTemp.put("out_trade_no", String.valueOf(order.getOrderid()));//用订单id即可

        sParaTemp.put("total_fee",Integer.parseInt((int)(order.getNeedpay()*100) + "")+"");
        //合作id
        sParaTemp.put("appid", Global.WX_APPID);
        //帐号
        sParaTemp.put("mch_id", Global.WX_PAY_MCH_ID);
        //商品详情
        sParaTemp.put("body", Global.PAY_SUBJECT);
        String sign = WXUtil.createSign(Global.WX_PAY_INPUT_CHARSET,sParaTemp,Global.WX_PAY_PARTER_KEY);
        sParaTemp.put("sign", sign);
        logger.info("wechat prepayid params:" + JSON.toJSONString(sParaTemp));
        String reuqestXml = WXUtil.getRequestXml(sParaTemp);
        logger.info("wechat prepay post xml data:"+reuqestXml);
        Map<String, String> respMap = XMLUtil.doXMLParse(HttpUtil.post(Global.WX_PAY_UNIFIED_URL,
                reuqestXml));
        logger.info("wechat prepayid request result：" + respMap!=null?respMap.toString():null);
        return respMap;
    }

    /**
     * app 构建请求参数
     * @param resultMap 微信返回值
     * @param order 订单实体
     * @return
     */
    private String AppBuildWxRequestStr(Map<String, String> resultMap, Order order){
        String result = "";
        String prepayid = (String.valueOf(resultMap.get("prepay_id")));
        logger.info("wechat get prepayid success,prepayid=" + prepayid);

        Map<String,String> retMap = new HashMap<String, String>();
        List<NameValuePair> payMap = new LinkedList<NameValuePair>();
        payMap.add(new NameValuePair("appid", Global.WX_APPID));
        retMap.put("appid", Global.WX_APPID);
        String noncestr = String.valueOf(resultMap.get("nonce_str"));
        payMap.add(new NameValuePair("noncestr", noncestr));
        retMap.put("noncestr", noncestr);
        payMap.add(new NameValuePair("package", "Sign=WXPay"));
        retMap.put("package", "Sign=WXPay");
        String partnerid = resultMap.get("mch_id");
        payMap.add(new NameValuePair("partnerid", partnerid));
        retMap.put("partnerid", partnerid);
        payMap.add(new NameValuePair("prepayid", prepayid));
        retMap.put("prepayid", prepayid);
        payMap.add(new NameValuePair("timestamp", WXUtil.getTimeStamp()));
        retMap.put("timestamp", WXUtil.getTimeStamp());
        String resultsign = WXUtil.wxSignByNvPair(Global.WX_PAY_INPUT_CHARSET,payMap,Global.WX_PAY_PARTER_KEY);
        payMap.add(new NameValuePair("sign", resultsign));
        retMap.put("sign", resultsign);
        result = JSON.toJSONString(retMap);
        logger.info("app wecaht 加密后:"+result);
        return result;

//        String result = "";
//        List<NameValuePair> payMap = new LinkedList<NameValuePair>();
//        payMap.add(new NameValuePair("appid", Global.WX_APPID));
//        payMap.add(new NameValuePair("noncestr", WXUtil.getNonceStr()));
//        payMap.add(new NameValuePair("package", "Sign=WXPay"));
//        payMap.add(new NameValuePair("partnerid", Global.WX_PAY_MCH_ID));
//        payMap.add(new NameValuePair("prepayid", prepayid));
//        payMap.add(new NameValuePair("timestamp", WXUtil.getTimeStamp()));
//        String resultsign = WXUtil.wxSignByNvPair(Global.WX_PAY_INPUT_CHARSET,payMap,Global.WX_PAY_PARTER_KEY);
//        payMap.add(new NameValuePair("sign", resultsign));
//        result = JSON.toJSONString(payMap);
//        logger.info("app wechat 加密后:"+result);
//        return result;
    }
}
