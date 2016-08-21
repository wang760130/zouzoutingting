package com.zouzoutingting.controllers;

import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.utils.HttpTestUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangyong on 16/6/19.
 */
public class PayControllerTest {
    @Test
    public void couponcheckTest() {
        String url = Global.HOST_URL + "/couponcheck";
        try {
            JSONObject result = HttpTestUtils.testUrl(url,
                    "couponcode=3JDBDJTS&token=/v8F00YWcAenQAcot9amZ8O3wDbfeVgzzPJcKdf/WWw=");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void createorderTest(){
        String url = Global.TEST_HOST_URL + "/createorder";
        try {
            JSONObject result = HttpTestUtils.testUrl(url,
                    "couponcode=3F8DAC9R&token=/v8F00YWcAenQAcot9amZ8O3wDbfeVgzzPJcKdf/WWw=&vid=22&cityid=1");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void couponPayTest(){
        String url = Global.TEST_HOST_URL + "/pay/couponpay";
        try {
            JSONObject result = HttpTestUtils.testUrl(url,
                    "orderid=83&token=/v8F00YWcAenQAcot9amZ8O3wDbfeVgzzPJcKdf/WWw=&vid=22&cityid=1");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void CouponGeneralTest(){
        String url = Global.TEST_HOST_URL + "/test/generalCode";
        try {
//            JSONObject result = HttpTestUtils.testUrl(url,
//                    "nunm=4000&amount=30&begin=2016-08-08 00:00:00&end=2017-08-08 00:00:00");
//            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void AliPrepayTest(){
        String url = Global.TEST_HOST_URL + "/pay/aliprepay";
        try {
            JSONObject result = HttpTestUtils.testUrl(url,
                    "orderid=135&token=/v8F00YWcAdWea+DHZHq14o+SATaZ+KWsZfG6AcjNAI=&vid=17&cityid=1");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void aliNotifyTest(){
        String url = Global.TEST_HOST_URL + "/notify/ali";
        try {
            String data = "zztt_info=\"135_100008_17_DZUXHO2E\"&partner=\"2016073100134649\"&seller_id" +
                    "=\"20881021693254322088102169325432" +
                    "\"&out_trade_no=\"135" +
                    "\"&subject=\"走走听听语音包\"&body=\"走走听听语音包\"&total_fee=\"0.0\"&notify_url=\"http://api.imonl.com/notify/ali?zztt_info=135_100008_17_DZUXHO2E\"&service=\"alipay.wap.create.direct.pay.by.user\"&_input_charset=\"utf-8\"&payment_type=\"1\"";
            Map<String, String> map = new HashMap<String, String>();
            String[] args = data.split("&");
            for(String k : args) {
                String[] k1 = k.split("=");
                map.put(k1[0], k1[1]);
            }

            String result = HttpTestUtils.httpClientPost(url, map, null);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckPayState(){
        String url = Global.TEST_HOST_URL + "/pay/checkresult";
        try {
            JSONObject result = HttpTestUtils.testUrl(url,
                    "orderid=1&token=/v8F00YWcAdWea+DHZHq14o+SATaZ+KWsZfG6AcjNAI=");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void WxPrepayTest(){
        String url = Global.TEST_HOST_URL + "/pay/wxprepay";
        try {
            JSONObject result = HttpTestUtils.testUrl(url,
                    "orderid=135&token=/v8F00YWcAdWea+DHZHq14o+SATaZ+KWsZfG6AcjNAI=&vid=17&cityid=1");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void wxNotifyTest(){
        String url = Global.TEST_HOST_URL + "/notify/wechat";
        try {
            String data = "zztt_info=\"135_100008_17_DZUXHO2E\"&partner=\"2016073100134649\"&seller_id" +
                    "=\"20881021693254322088102169325432" +
                    "\"&out_trade_no=\"135" +
                    "\"&subject=\"走走听听语音包\"&body=\"走走听听语音包\"&total_fee=\"0.0\"&notify_url=\"http://api.imonl" +
                    ".com/notify/wechat?zztt_info=135_100008_17_DZUXHO2E\"&service=\"alipay.wap.create.direct.pay.by" +
                    ".user\"&_input_charset=\"utf-8\"&payment_type=\"1\"";
            Map<String, String> map = new HashMap<String, String>();
            String[] args = data.split("&");
            for(String k : args) {
                String[] k1 = k.split("=");
                map.put(k1[0], k1[1]);
            }

            String result = HttpTestUtils.httpClientPost(url, map, null);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
