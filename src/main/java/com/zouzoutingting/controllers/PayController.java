package com.zouzoutingting.controllers;

import com.zouzoutingting.enums.CouponStateEnum;
import com.zouzoutingting.enums.OrderStateEnum;
import com.zouzoutingting.model.Coupon;
import com.zouzoutingting.model.Order;
import com.zouzoutingting.model.PrePayResult;
import com.zouzoutingting.service.ICouponService;
import com.zouzoutingting.service.IOrderService;
import com.zouzoutingting.service.IPayService;
import com.zouzoutingting.utils.RequestParamUtil;
import com.zouzoutingting.utils.XMLUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangyong on 16/6/18.
 * 支付接口
 */
@Controller
public class PayController extends BaseController {
    private static final Logger logger = Logger.getLogger(PayController.class);
    @Autowired
    private ICouponService couponService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPayService payService;

    @RequestMapping(value = "/couponcheck", method = RequestMethod.POST)
    public void couponCheck(HttpServletRequest request, HttpServletResponse response){
        String couponCode = RequestParamUtil.getParam(request, "couponcode", "");//优惠码
        if(StringUtils.isNotBlank(couponCode)){
            Coupon coupon = couponService.getCouponByCode(couponCode);
            if(coupon==null){//券为null
                logger.error("券码无效:"+couponCode);
                gzipCipherResult(-3, "券码错误", NULL_OBJECT, request, response);
            }else{
                gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, coupon, request, response);
            }
        }else{//参数错误
            logger.error("参数错误,couponcode 为 null");
            gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
        }
    }

    @RequestMapping(value = "/createorder", method = RequestMethod.POST)
    public void createorder(HttpServletRequest request, HttpServletResponse response){
        Long uid = RequestParamUtil.getLongParam(request, "uid", -1L);
        Integer cityid = RequestParamUtil.getIntegerParam(request, "cityid", -1);
        Long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        String couponCode = RequestParamUtil.getParam(request, "couponcode", "");//优惠码
        Double money = RequestParamUtil.getDoubleParam(request, "money", 0.0);//订单总价
        Double cash = RequestParamUtil.getDoubleParam(request, "cash", 0.0);//支付金额

        boolean isillegle = checkcreateorder(uid, cityid, vid, couponCode, money, cash);
        if(isillegle){//非法
            gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
        }else{
            //1.校验是否已经有订单
            Order order = orderService.getOrderByUidAndVid(uid, vid);
            if(order!=null){
                if(order.getState()==OrderStateEnum.Finish.getState()){
                    gzipCipherResult(1, "您已经购买了当前景点", NULL_OBJECT, request, response);
                    return;
                }
            }else{
                //生成订单
                order = buildOrder(uid, cityid, vid, couponCode, money, cash);
            }
            if(order!=null) {
                Map<String, String> resultMap = new HashMap<String, String>();
                resultMap.put("orderid", order.getOrderid()+"");
                gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, resultMap, request, response);
            }else{
                gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
            }
        }
    }
    private boolean checkcreateorder(Long uid, Integer cityid, Long vid, String couponCode, Double money, Double cash){
        boolean isillegle = false;
        if(uid<0 || cityid<0 || vid<0 || cash<=0.0 || money<=0.0){
            isillegle = true;
        }else{
            if(!StringUtils.isEmpty(couponCode)){
                Coupon coupon = couponService.getCouponByCode(couponCode);
                if(coupon==null){
                    logger.error("coupon code illegle "+couponCode);
                    isillegle = true;
                }else {
                    if(coupon.getState()!= CouponStateEnum.Available.getState() || !money.equals(coupon.getAmount() +
                            cash)){//0代表可用
                        logger.error("money amount is wrong or coupon state wrong. total money:"+money +", " +
                                "cash:"+cash+", " +
                                "couponAmount:"+coupon.getAmount()+"; coupon state:"+coupon.getState());
                        isillegle = true;
                    }
                }
            }
        }
        return isillegle;
    }
    private Order buildOrder(Long uid, Integer cityid, Long vid, String couponCode, Double money, Double cash) {
        Order order = new Order();
        order.setCode(couponCode);
        order.setNeedpay(cash);
        order.setState(OrderStateEnum.Jinx.getState());
        order.setTotal(money);
        order.setUid(uid);
        order.setVid(vid);
        return orderService.insertOrder(order);
    }
    @RequestMapping(value = "/pay/wxprepay", method = RequestMethod.POST)
    public void wxPrePay(HttpServletRequest request, HttpServletResponse response){
        Long uid = RequestParamUtil.getLongParam(request, "uid", -1L);
        Long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        Long orderid = RequestParamUtil.getLongParam(request, "orderid", -1L);

        //1.获取order
        Order order = null;
        order = orderService.getOrderByID(orderid);
        //2.调用微信
        PrePayResult prePayResult = null;
        try {
            prePayResult = payService.getWxPreyPayInfo(order);
        } catch (Exception e) {
            logger.error("调用微信预付Error uid:"+uid+",vid:"+vid+",orderid:"+orderid, e);
        }
        //返回结果
        if(prePayResult!=null){
            if(prePayResult.getResult()){
                gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, prePayResult, request, response);
            }else{
                gzipCipherResult(RETURN_CODE_EXCEPTION, "支付异常", prePayResult, request, response);
            }
        }else{
            gzipCipherResult(RETURN_CODE_EXCEPTION, "支付异常,请重试", NULL_OBJECT, request, response);
        }
    }

    @RequestMapping(value = "/pay/aliprepay", method = RequestMethod.POST)
    public void aliPrePay(HttpServletRequest request, HttpServletResponse response){
        Long uid = RequestParamUtil.getLongParam(request, "uid", -1L);
        Long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        Long orderid = RequestParamUtil.getLongParam(request, "orderid", -1L);

        //1.获取order
        Order order = null;
        order = orderService.getOrderByID(orderid);
        //2.调用微信
        PrePayResult prePayResult = null;
        try {
            prePayResult = payService.getAliPreyPayInfo(order);
        } catch (Exception e) {
            logger.error("调用微信预付Error uid:"+uid+",vid:"+vid+",orderid:"+orderid, e);
        }
        //返回结果
        if(prePayResult!=null){
            if(prePayResult.getResult()){
                gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, prePayResult, request, response);
            }else{
                gzipCipherResult(RETURN_CODE_EXCEPTION, "支付异常", prePayResult, request, response);
            }
        }else{
            gzipCipherResult(RETURN_CODE_EXCEPTION, "支付异常,请重试", NULL_OBJECT, request, response);
        }
    }

    /**
     * 微信回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notify/wechat")
    public void WxNotify(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        try{
            String param = RequestParamUtil.getParam(request, "zztt_info", "");// orderid_uid_vid_couponcode
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String[] pArray = param.split("_");
            Map<String,String> params = new HashMap<String,String>();
            params.put("orderid", pArray[0]);
            params.put("uid", pArray[1]);
            params.put("vid", pArray[2]);
            params.put("couponcode", pArray[3]);
            result = payService.wxPayNotify(br, params);
        }catch (Exception e){
            logger.error("wx notify controller error", e);
        }
        if(result){
            gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, NULL_OBJECT, request, response);
        }else {
            gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
        }
    }

    /**
     * ali回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notify/ali")
    public void AliNotify(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        try{
            String param = RequestParamUtil.getParam(request, "zztt_info", "");// orderid_uid_vid_couponcode
            Map<String, String[]> requestParams = request.getParameterMap();
            Map<String,String> params = new HashMap<String,String>();
            String[] pArray = param.split("_");
            params.put("orderid", pArray[0]);
            params.put("uid", pArray[1]);
            params.put("vid", pArray[2]);
            params.put("couponcode", pArray[3]);

            ret = payService.AliPayNotify(requestParams, params);
        }catch (Exception e){
            logger.error("ali notify controller error", e);
        }
        if(ret){
            gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, NULL_OBJECT, request, response);
        }else {
            gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
        }
    }

}
