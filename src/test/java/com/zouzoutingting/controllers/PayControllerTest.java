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
    public void viewspotTest() {
        String url = Global.HOST_URL + "/couponcheck";
        try {
            JSONObject result = HttpTestUtils.testUrl(url,"couponcode=12132");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
