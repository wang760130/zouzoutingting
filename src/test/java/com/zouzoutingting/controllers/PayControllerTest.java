package com.zouzoutingting.controllers;

import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.utils.HttpTestUtils;
import org.junit.Test;

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
            JSONObject result = HttpTestUtils.testUrl(url,
                    "nunm=2000&amount=30&begin=2016-08-03 00:00:00&end=2017-08-03 00:00:00");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
