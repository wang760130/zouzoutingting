package com.zouzoutingting.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.zouzoutingting.model.*;
import com.zouzoutingting.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zouzoutingting.components.cache.CacheMap;
import com.zouzoutingting.enums.CouponStateEnum;
import com.zouzoutingting.enums.OrderStateEnum;
import com.zouzoutingting.utils.CouponCodeUtil;
import com.zouzoutingting.utils.RequestParamUtil;

/**
 * Created by zhangyong on 16/6/18.
 * 支付接口
 */
@Controller
public class PayController extends BaseController {
    private static final Logger logger = Logger.getLogger(PayController.class);
    private static final int TOTAL_TIMES = 5;
    private static final int COUPON_LENGTH = 8;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 缓存用户使用优惠券错误次数，60 * 60 * 1000
    private static final CacheMap<String, Integer> timesCache = new CacheMap<String, Integer>(60*60*1000);
    
    @Autowired
    private ICouponService couponService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPayService payService;
    @Autowired
    private IViewSpotService viewSpotService;
    @Autowired
    private IThirdPayService thirdPayService;
    @Autowired
    private IConfigService configService;

    @RequestMapping(value = "/couponcheck", method = RequestMethod.POST)
    public void couponCheck(HttpServletRequest request, HttpServletResponse response){
        String couponCode = RequestParamUtil.getParam(request, "couponcode", "");//优惠码
        Long uid = Long.valueOf(request.getAttribute("uid") + "");
        int code = -3;//错误
        String msg = "";
        Object entity = NULL_OBJECT;
        //校验次数
        Integer checkTimes = 0;
        String key = "couponCheckTimes_"+uid;

        if(StringUtils.isNotBlank(couponCode) && uid > 0L){
        	checkTimes = timesCache.get(key);
        	if(checkTimes == null) {
        		checkTimes = 0;
        	}
            if(checkTimes >= TOTAL_TIMES){
                code = -4;
                msg = "您输入次数已达上限,请一小时后重试";
                gzipCipherResult(code, msg, entity, request, response);
                return;
            }
            Coupon coupon = couponService.getCouponByCode(couponCode);
            if(coupon==null){//券为null
                logger.error("券码无效:"+couponCode);
                msg = "券码错误,";
            }else{
                if(coupon.getState()==CouponStateEnum.Available.getState()) {
                    code =RETURN_CODE_SUCCESS;
                    msg = RETURN_MESSAGE_SUCCESS;
                    entity = coupon;
                }else{
                    logger.error("券码已使用:"+couponCode);
                    msg = "券码已使用,";
                }
            }
        }else{//参数错误
            logger.error("参数错误,couponcode 为 "+couponCode+" uid:"+uid);
            code = RETURN_CODE_PARAMETER_ERROR;
            msg = RETUEN_MESSAGE_PARAMETER_ERROR+",";
        }

        if(code==RETURN_CODE_SUCCESS){
        	timesCache.delete(key);
        }else{
        	checkTimes ++;
            int leftTime = TOTAL_TIMES - checkTimes;
            msg += "剩余"+((leftTime > 0) ? leftTime : 0)+"次输入机会";
            timesCache.put(key, checkTimes);
        }
        gzipCipherResult(code, msg, entity, request, response);
    }

    @RequestMapping(value = "/createorder", method = RequestMethod.POST)
    public void createorder(HttpServletRequest request, HttpServletResponse response){
        Long uid = Long.valueOf(request.getAttribute("uid") + "");
        Integer cityid = RequestParamUtil.getIntegerParam(request, "cityid", -1);
        Long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        String couponCode = RequestParamUtil.getParam(request, "couponcode", "");//优惠码
//        Double money = RequestParamUtil.getDoubleParam(request, "money", 0.0);//订单总价
//        Double cash = RequestParamUtil.getDoubleParam(request, "cash", 0.0);//支付金额

        Map<String, String> resultMap = new HashMap<String, String>();


        List<Double> moneyList = new ArrayList<Double>();
        boolean isillegle = checkcreateorder(uid, cityid, vid, couponCode, moneyList);
        if(isillegle){//非法
            gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
        }else{
            Double money = moneyList.get(0);//总量
            Double cash = moneyList.get(1);//需支付
            //1.校验是否已经有订单
            Order order = orderService.getOrderByUidAndVid(uid, vid);
            if(order!=null){
                resultMap.put("orderid", order.getOrderid()+"");
                if(order.getState()==OrderStateEnum.Finish.getState()){
                    gzipCipherResult(1, "您已经购买了当前景点", resultMap, request, response);
                    return;
                }else {//校验是否换券
                    if(order.getCode()!=null && !order.getCode().equals(couponCode))
                    order.setCode(couponCode);
                    order.setNeedpay(cash);
                    order.setState(OrderStateEnum.Jinx.getState());
                    order.setTotal(money);
                    order.setUid(uid);
                    order.setVid(vid);
                    orderService.update(order);
                }
            }else{
                //生成订单
                order = buildOrder(uid, cityid, vid, couponCode, money, cash);
            }
            if(order!=null) {
                resultMap.put("orderid", order.getOrderid()+"");
                gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, resultMap, request, response);
            }else{
                gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
            }
        }
    }
    private boolean checkcreateorder(Long uid, Integer cityid, Long vid, String couponCode, List<Double> out){
        boolean isillegle = false;
        logger.info("uid:"+uid+",cityid:"+cityid+",vid:"+vid+",couponcode:"+couponCode);
        if(uid<0 || cityid<0 || vid<0){
            isillegle = true;
        }else{
            if(!StringUtils.isEmpty(couponCode)){
                Coupon coupon = couponService.getCouponByCode(couponCode);
                ViewSpot viewSpot = viewSpotService.getViewSpotByID(vid, uid);
                if(coupon==null || viewSpot==null){
                    logger.error("coupon code illegle "+couponCode +" or viewspot is null vid:"+vid);
                    isillegle = true;
                }else {
                    if(coupon.getState()!= CouponStateEnum.Available.getState() && viewSpot.getPrice()<coupon
                            .getAmount())
                    {//0代表可用
                        logger.error("money amount is wrong or coupon state wrong. total money:"+viewSpot.getPrice() +", " +
                                "couponAmount:"+coupon.getAmount()+"; coupon state:"+coupon.getState());
                        isillegle = true;
                    }else {
                        out.add(viewSpot.getPrice());
                        BigDecimal total = new BigDecimal(Double.toString(viewSpot.getPrice()));
                        BigDecimal counprice = new BigDecimal(Double.toString(coupon.getAmount()));
                        out.add(total.subtract(counprice).doubleValue());
                    }
                }
            }else{//不使用券支付
                ViewSpot viewSpot = viewSpotService.getViewSpotByID(vid, uid);
                if(viewSpot!=null){
                    out.add(viewSpot.getPrice());
                    out.add(viewSpot.getPrice());
                }else {
                    isillegle = true;//不合法
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
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        return orderService.insertOrder(order);
    }

    /**
     * 验证码支付
     * @param request 请求
     * @param response 回执
     */
    @RequestMapping(value = "/pay/couponpay", method = RequestMethod.POST)
    public void couponPay(HttpServletRequest request, HttpServletResponse response){
        Long uid = Long.valueOf(request.getAttribute("uid") + "");
        Long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        Long orderid = RequestParamUtil.getLongParam(request, "orderid", -1L);

        if(uid>0 && vid>0 && orderid>0) {
            //1.获取order
            Order order = null;
            order = orderService.getOrderByID(orderid);
            if(order!=null){
                if(order.getState() == OrderStateEnum.Finish.getState()){
                    gzipCipherResult(1, "您已经购买了当前景点", NULL_OBJECT, request, response);
                }else{
                    String couponCode = order.getCode();
                    if(couponCode!=null) {
                        //2.获取券详情
                        Coupon coupon = couponService.getCouponByCode(couponCode);
                        if (coupon == null) {
                            logger.error("无效couponcode " + couponCode + " oid:" + orderid + ", vid:" + vid + " ,uid:" + uid);
                        } else {
                            if (coupon.getState() != CouponStateEnum.Available.getState() || order.getTotal() > coupon
                                    .getAmount()) {//券支付是否ok
                                logger.error("券金额不够 couponcode:" + couponCode + " couponMoney:" + coupon.getAmount() + "," +
                                        "orderTotal:" + order.getTotal() +
                                        " " +
                                        "oid:" + orderid
                                        + ", " +
                                        "vid:" + vid + " ," +
                                        "uid:" + uid);
                                gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
                            }else{
                                //3.更新订单状态+coupon状态
                                boolean isused = couponService.useCounpon(coupon, orderid);
                                if(isused){
                                    boolean ispayed = orderService.CounponPay(order, coupon.getCouponid());
                                    if(!ispayed){
                                        logger.info("order pay state not updated rollback coupon oid:" + orderid + "," +
                                                " vid:" + vid + " ,uid:" + uid +" counid:"+coupon.getCouponid()+", " +
                                                "couponCode:"+couponCode);
                                        boolean rollbackRet = couponService.rollBackCouponPay(coupon);
                                        logger.info("rollback result "+rollbackRet + "oid:" + orderid + ", vid:" +
                                                vid + " ,uid:" + uid+" counid:"+coupon.getCouponid()+", " +
                                                "couponCode:"+couponCode);
                                        //
                                        gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, "支付失败,订单状态异常", NULL_OBJECT,
                                                request,
                                                response);
                                    }else{
                                        logger.info("券支付成功 oid:" + orderid + ", vid:" +
                                        vid + " ,uid:" + uid+" counid:"+coupon.getCouponid()+", " +
                                                "couponCode:"+couponCode);
                                        gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, order,
                                                    request,
                                                    response);

                                    }
                                }
                            }
                        }
                    }
                }

            }else{//无订单
                logger.error("orderid 无订单 oid:"+orderid +", vid:"+vid+" ,uid:"+uid);
                gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
            }
        }else{
            logger.error("券支付参数错误 oid:"+orderid +", vid:"+vid+" ,uid:"+uid);
            gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
        }

    }

    @RequestMapping(value = "/pay/wxprepay", method = RequestMethod.POST)
    public void wxPrePay(HttpServletRequest request, HttpServletResponse response){
        Long uid = Long.valueOf(request.getAttribute("uid") + "");
        Long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        Long orderid = RequestParamUtil.getLongParam(request, "orderid", -1L);

        //1.获取order
        Order order = null;
        order = orderService.getOrderByID(orderid);
        if(order!=null && order.getState()!=OrderStateEnum.Finish.getState() && order.getUid()==uid) {
            //2.调用微信
            PrePayResult prePayResult = null;
            try {
                prePayResult = payService.getWxPreyPayInfo(order);
            } catch (Exception e) {
                logger.error("调用微信预付Error uid:" + uid + ",vid:" + vid + ",orderid:" + orderid, e);
            }
            //返回结果
            returnPrePayResult(prePayResult, request, response);
        }else{
            if(order==null){
                logger.error("wxprepay 支付参数错误 oid:"+orderid +", vid:"+vid+" ,uid:"+uid);
                gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
            }else {
                if(order.getState()==OrderStateEnum.Finish.getState()) {
                    logger.error("wxprepay 已支付 oid:" + orderid + ", vid:" + vid + " ,uid:" + uid);
                    gzipCipherResult(1, "您已经购买了当前景点", NULL_OBJECT, request, response);
                }else{
                    logger.error("wxprepay 非本人 oid:" + orderid + ", vid:" + vid + " ,uid:" + uid);
                    gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
                }
            }
        }
    }

    @RequestMapping(value = "/pay/aliprepay", method = RequestMethod.POST)
    public void aliPrePay(HttpServletRequest request, HttpServletResponse response){
        Long uid = Long.valueOf(request.getAttribute("uid") + "");
        Long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        Long orderid = RequestParamUtil.getLongParam(request, "orderid", -1L);

        //1.获取order
        Order order = null;
        order = orderService.getOrderByID(orderid);
        if(order!=null && order.getState()!=OrderStateEnum.Finish.getState() && order.getUid()==uid) {
            //2.拼接参数
            PrePayResult prePayResult = null;
            try {
                prePayResult = payService.getAliPreyPayInfo(order);
            } catch (Exception e) {
                logger.error("调用微信预付Error uid:" + uid + ",vid:" + vid + ",orderid:" + orderid, e);
            }
            //返回结果
            returnPrePayResult(prePayResult, request, response);
        }else{
            if(order==null){
                logger.error("aliprey支付参数错误 oid:"+orderid +", vid:"+vid+" ,uid:"+uid);
                gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
            }else {
                if(order.getState()==OrderStateEnum.Finish.getState()) {
                    logger.error("aliprey 已支付 oid:" + orderid + ", vid:" + vid + " ,uid:" + uid);
                    gzipCipherResult(1, "您已经购买了当前景点", NULL_OBJECT, request, response);
                }else{
                    logger.error("aliprey 非本人 oid:" + orderid + ", vid:" + vid + " ,uid:" + uid);
                    gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
                }
            }
        }
    }
    private void returnPrePayResult(PrePayResult prePayResult, HttpServletRequest request, HttpServletResponse response){
        if (prePayResult != null) {
            if (prePayResult.getResult()) {
                gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, prePayResult, request, response);
            } else {
                gzipCipherResult(RETURN_CODE_EXCEPTION, "支付异常", prePayResult, request, response);
            }
        } else {
            gzipCipherResult(RETURN_CODE_EXCEPTION, "支付异常,请重试", NULL_OBJECT, request, response);
        }
    }

    /**
     * ali回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notify/ali/{couponcode}/{orderid}/{uid}/{vid}")
    public void AliNotify(@PathVariable(value="orderid") long orderid, @PathVariable(value = "uid") long uid,
                          @PathVariable(value = "vid") long vid, @PathVariable(value = "couponcode") String couponcode,HttpServletRequest request, HttpServletResponse response){

        boolean ret = false;
        try{
            Map<String, String[]> requestParams = request.getParameterMap();
            Map<String,String> params = new HashMap<String,String>();

            params.put("orderid", orderid+"");
            params.put("uid", uid+"");
            params.put("vid", vid+"");
            if(couponcode.equals("null")){
                couponcode = "";
            }
            params.put("couponcode", couponcode);

            ret = payService.AliPayNotify(requestParams, params);
        }catch (Exception e){
            logger.error("ali notify controller error", e);
        }
        String result = "fail";
        if(ret){
            result = "success";
        }
        stringResult(result, response);
    }

    @RequestMapping(value = "pay/checkresult", method = RequestMethod.POST)
    public void AliCheckResult(HttpServletRequest request, HttpServletResponse response){
        Long uid = Long.valueOf(request.getAttribute("uid") + "");
        Long orderid = RequestParamUtil.getLongParam(request, "orderid", -1L);
        Integer count = RequestParamUtil.getIntegerParam(request, "count", 1);

        if(uid>0 && orderid>0){
            Order order = orderService.getOrderByID(orderid);
            if(order!=null && order.getUid() == uid){
                boolean payed = false;
                if(order.getState()==OrderStateEnum.Finish.getState()){
                    payed = true;
                    logger.info("uid:"+uid+",orderid:"+orderid+" 已支付");
                }else{
                    logger.info("uid:"+uid+",orderid:"+orderid+" db未支付 "+ JSON.toJSONString(order));
                    //查询第三方后台

                }
                //
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", payed);
                gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, map, request, response);
            }else {
                logger.error("checkresult 非本人 参数错误 uid" + uid + ", orderid:" + orderid);
                gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
            }

        }else{
            logger.error("checkresult 参数错误 uid" + uid + ", orderid:" + orderid);
            gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
        }

    }

    /**
     * 微信回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notify/wechat/{couponcode}/{orderid}/{uid}/{vid}")
    public void WxNotify(@PathVariable(value="orderid") long orderid, @PathVariable(value = "uid") long uid,
                         @PathVariable(value = "vid") long vid, @PathVariable(value = "couponcode") String couponcode,
                         HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            Map<String,String> params = new HashMap<String,String>();
            params.put("orderid", orderid+"");
            params.put("uid", uid+"");
            params.put("vid", vid+"");
            if(couponcode.equals("null")){
                couponcode = "";
            }
            params.put("couponcode", couponcode);
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

    @RequestMapping(value = "/test/generalCode")
    public void GeneralCode(HttpServletRequest request, HttpServletResponse response) {
        Integer num = RequestParamUtil.getIntegerParam(request, "nunm", 0);//数量
        Double amount = RequestParamUtil.getDoubleParam(request, "amount", 0.0);
        String beginDateStr = RequestParamUtil.getParam(request, "begin", "");
        String endDateStr = RequestParamUtil.getParam(request, "end", "");

        if(num>0 && StringUtils.isNotBlank(beginDateStr) && StringUtils.isNotBlank(endDateStr) && amount>0.0){
            Date startDate = null;
            Date endDate = null;

            try {
                startDate = sdf.parse(beginDateStr);
                endDate = sdf.parse(endDateStr);
            } catch (ParseException e) {
                logger.error("日期参数传入错误", e);
            }

            int successLen = 0;
            StringBuilder stringBuilder = new StringBuilder();
            if(startDate!=null && endDate!=null){
                for(int i = 0; i<num; i++) {
                    Coupon coupon = new Coupon();
                    coupon.setAmount(amount);
                    coupon.setEndtime(endDate);
                    coupon.setStarttime(startDate);
                    String code = CouponCodeUtil.generateString(COUPON_LENGTH);
                    coupon.setCode(code);
                    coupon.setState(CouponStateEnum.Available.getState());
                    try {
                        long id = couponService.save(coupon);
                        if(id>0){
                            successLen++;
                            stringBuilder.append(code+",");
                        }
                    } catch (Exception e) {
                        logger.error("插入失败", e);
                    }
                }
            }
            stringBuilder.append("||数量:"+successLen);

            gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, stringBuilder.toString(), request, response);
        }else {
            gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
        }

    }

    @RequestMapping(value = "/pay/config")
    public void PayConfig(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> map = new HashMap<String, String>();
        String config = "0";

        try {
            String tconfig = configService.getCoinfig("onlinepay");
            if(tconfig!=null){
                config = tconfig;
            }
        } catch (Exception e) {
            logger.error("getconfig onlinepay error", e);
        }
        map.put("onlinepay", config);
        gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, map, request, response);
    }

    public static void main(String[] args){
        Double total = 0.03;
        Double amount = 0.02;
        BigDecimal total1 = new BigDecimal(Double.toString(total));
        BigDecimal counprice = new BigDecimal(Double.toString(amount));
        System.out.println(total1.subtract(counprice).doubleValue());
    }
}
