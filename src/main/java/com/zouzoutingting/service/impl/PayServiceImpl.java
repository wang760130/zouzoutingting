package com.zouzoutingting.service.impl;

import com.alibaba.fastjson.JSON;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.enums.OrderStateEnum;
import com.zouzoutingting.model.Order;
import com.zouzoutingting.model.PrePayResult;
import com.zouzoutingting.service.IPayService;
import com.zouzoutingting.utils.*;
import com.zouzoutingting.utils.alipay.AliParamCore;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.*;

/**
 * Created by zhangyong on 16/6/19.
 */
@Service("payService")
public class PayServiceImpl implements IPayService{
    private static final Logger logger = Logger.getLogger(PayServiceImpl.class);
    @Autowired
    private IDao<Order> orderDao;

    @Override
    public PrePayResult getWxPreyPayInfo(Order order) throws Exception{
        PrePayResult resultDto = new PrePayResult();
        resultDto.setOrderid(order.getOrderid());
        resultDto.setResult(false);
        Map<String, String> resultMap = getWxPreyPayInfoFromWx(order);
        String respresut = String.valueOf(resultMap.get("result_code"));
        //拼接调起支付参数
        if((respresut).equals("SUCCESS")){
            String prepayid = (String.valueOf(resultMap.get("prepay_id")));
            logger.info("wechat get prepayid success,prepayid=" + prepayid);
            resultDto.setParamStr(AppBuildWxRequestStr(prepayid, order));
            resultDto.setResult(true);
            logger.info("wechat jointPayParams success,orderid={}"+order.getOrderid() +",result={}" + resultDto);
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
                logger.info("PayV34ServiceImpl wxPayParamsNotify AppPayService.WXNotify 返回结果：---------" + ret);
                return ret;
            }
            ret = wxPayLogicNotify(params,requestMap);
        }catch (Exception e){
            logger.error("wx notify Error", e);
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
        logger.info("PayServiceImpl wxPayParamsNotify:得到微信回调内容。。。。。。。---------" + requestResult);
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
            Order order = orderDao.load(Long.parseLong(orderidStr));
            if(order!=null){
                if(order.getState()!= OrderStateEnum.Finish.getState()){
                    logger.info("wx orderid:"+orderidStr+" payed state "+order.getState()+"-->"+OrderStateEnum.Finish
                            .getState());
                    order.setState(OrderStateEnum.Finish.getState());
                    orderDao.save(order);
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

    @Override
    public PrePayResult getAliPreyPayInfo(Order order) throws Exception {
        PrePayResult resultDto = new PrePayResult();
        resultDto.setOrderid(order.getOrderid());
        resultDto.setResult(false);

        Map<String, String> sParaTemp = new HashMap<String, String>();
        //notifyurl
        String extInfo = order.getOrderid()  + "_" + order.getUid() + "_" + order.getVid() + "_" + order.getCode();
        String notifyUrl = PropManager.getSingletonInstance().getProperty("ali_notify_url");
        notifyUrl = notifyUrl + "zztt_info=" + extInfo;
        sParaTemp.put("service", Global.Ali_PAY_SERVICE);
        sParaTemp.put("_input_charset", Global.ALI_PAY_INPUT_CHARSET);
        sParaTemp.put("payment_type",Global.ALI_PAY_PAYMENT_TYPE);
        sParaTemp.put("notify_url", notifyUrl);
        sParaTemp.put("out_trade_no", order.getOrderid()+"");//订单id做账单id
        sParaTemp.put("total_fee", order.getNeedpay() + "");
        //合作id
        sParaTemp.put("partner", Global.ALI_PAY_PARTNER_ID);
        //帐号
        sParaTemp.put("seller_id", Global.ALI_PAY_PARTNER_ID);
        //商品详情
        sParaTemp.put("body", Global.PAY_SUBJECT);
        //商品名称
//        if(StringUtils.isNotBlank(prePayDto.getSubject())){
//            sParaTemp.put("subject", prePayDto.getSubject());
//        }else{
        sParaTemp.put("subject", Global.PAY_SUBJECT);
//        }
//        if(StringUtils.isNotBlank(prePayDto.getReturnUrl())){
//            sParaTemp.put("return_url", prePayDto.getReturnUrl());
//        }
//        if(StringUtils.isNotBlank(prePayDto.getShowUrl())){
//            sParaTemp.put("show_url", prePayDto.getShowUrl());
//        }
        try {
            String params = AliParamCore.AppBuildAliRequestStr(sParaTemp, Global.ALI_PAY_PARTNER_PRIVATE_KEY);
            logger.info("ali ret is : " + params);
            resultDto.setResult(true);
            resultDto.setParamStr(params);
            logger.info("ali jointPayParams success,orderid=" + order.getOrderid());
        } catch (Exception e) {
            logger.error("ali buildRequestStr err,date=" + JSON.toJSONString(sParaTemp),e);
            resultDto.setParamStr("加签失败");
        }
        return null;
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
        String orderidStr = (String)requestParams.get("out_trade_no");
        if(StringUtils.isNotBlank(orderidStr) && orderidStr.equals(params.get("orderid"))){
            Order order = orderDao.load(Long.parseLong(orderidStr));
            if(order!=null){
                if(order.getState()!= OrderStateEnum.Finish.getState()){
                    logger.info("ali orderid:"+orderidStr+" payed state "+order.getState()+"-->"+OrderStateEnum.Finish
                            .getState());
                    order.setState(OrderStateEnum.Finish.getState());
                    orderDao.save(order);
                }
                result = true;
            }else{
                logger.error("orderID:"+orderidStr+" aliNotify get order null!!");
            }
        }else{
            logger.error("orderID:"+orderidStr+" aliNotify param Wrong!!");
        }
        return result;
    }

    private Map<String, String> getWxPreyPayInfoFromWx(Order order) throws Exception{
        SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
        //notifyurl
        String extInfo = order.getOrderid()  + "_" + order.getUid() + "_" + order.getVid() + "_" + order.getCode();
        String notifyUrl = PropManager.getSingletonInstance().getProperty("wx_notify_url");
        notifyUrl = notifyUrl + "zztt_info=" + extInfo;

        sParaTemp.put("trade_type", Global.WX_Trade_Type);
        sParaTemp.put("nonce_str", WXUtil.getNonceStr());
        sParaTemp.put("notify_url", notifyUrl);
        sParaTemp.put("out_trade_no", String.valueOf(order.getOrderid()));//用订单id即可
        sParaTemp.put("total_fee",order.getNeedpay() + "");
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
        Map<String, String> respMap = XMLUtil.doXMLParse(HttpUtil.post(Global.WX_PAY_UNIFIED_URL,
                reuqestXml));
        logger.info("wechat prepayid request result：" + respMap!=null?respMap.toString():null);
        return respMap;
    }

    /**
     * app 构建请求参数
     * @param prepayid 微信预付id
     * @param order 订单实体
     * @return
     */
    private String AppBuildWxRequestStr(String prepayid, Order order){
        String result = "";
        List<NameValuePair> payMap = new LinkedList<NameValuePair>();
        payMap.add(new NameValuePair("appid", Global.WX_APPID));
        payMap.add(new NameValuePair("noncestr", WXUtil.getNonceStr()));
        payMap.add(new NameValuePair("package", "Sign=WXPay"));
        payMap.add(new NameValuePair("partnerid", Global.WX_PAY_MCH_ID));
        payMap.add(new NameValuePair("prepayid", prepayid));
        payMap.add(new NameValuePair("timestamp", WXUtil.getTimeStamp()));
        String resultsign = WXUtil.wxSignByNvPair(Global.WX_PAY_INPUT_CHARSET,payMap,Global.WX_PAY_PARTER_KEY);
        payMap.add(new NameValuePair("sign", resultsign));
        result = JSON.toJSONString(payMap);
        logger.info("app wecaht 加密后:"+result);
        return result;
    }
}
