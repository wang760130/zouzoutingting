package com.zouzoutingting.service;

import com.zouzoutingting.model.Order;
import com.zouzoutingting.model.PrePayResult;

import java.io.BufferedReader;
import java.util.Map;

/**
 * Created by zhangyong on 16/6/19.
 */
public interface IPayService {

    public PrePayResult getWxPreyPayInfo(Order order) throws Exception;

    /**
     * 微信回调逻辑
     * @param br requestString
     * @param params url参数param
     * @return true/false
     */
    public boolean wxPayNotify(BufferedReader br, Map<String,String> params);

    public PrePayResult getAliPreyPayInfo(Order order) throws Exception;

    /**
     * 阿里支付回调逻辑
     * @param requestMap request 参数
     * @param params url获取参数解析map
     * @return true/false
     */
    public boolean AliPayNotify(Map requestMap, Map params);
}
