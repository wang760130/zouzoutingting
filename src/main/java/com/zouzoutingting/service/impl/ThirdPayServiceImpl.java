package com.zouzoutingting.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.serializer.ObjectArraySerializer;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.model.Order;
import com.zouzoutingting.service.IThirdPayService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangyong on 16/8/21.
 */
public class ThirdPayServiceImpl implements IThirdPayService {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public boolean isPayedBySearchThird(Order order) {
        boolean ispayed = false;
        if(order!=null){
            String data = order.getComment();
            if(data!=null){
                Map<String, String> map = (Map<String, String>) JSONUtils.parse(data);
                if(map!=null){
                    //判读支付渠道
                    String payType = map.get("paytype");
                    if(payType!=null){
                        if(payType.equals("ali")){//阿里支付,调用阿里查询接口

                        }else if(payType.equals("wx")){//微信支付

                        }
                    }
                }
            }
        }
        return ispayed;
    }

    private boolean AliQueryTrade(String aliTradeid, long orderid){
        boolean ispayed = false;
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("app_id", Global.getAliAppID());
        requestMap.put("method", "alipay.trade.query");
        requestMap.put("charset", Global.ALI_PAY_INPUT_CHARSET);
        requestMap.put("sign_type", "RSA");
        requestMap.put("timestamp", simpleDateFormat.format(new Date()));
        requestMap.put("version",Global.ALI_PAY_INTERFACE_VERSION);
        Map<String, String> bizMap = new HashMap<String, String>();
        bizMap.put("out_trade_no", orderid+"");
        bizMap.put("trade_no", aliTradeid);
        requestMap.put("biz_content",bizMap);
        requestMap.put("sign","");
//        String query_url = Global.getAliPayGatewayUrl() + "service=notify_verify&partner=" + partner + "&notify_id=" +
//                notify_id;

        return  ispayed;
    }
}
