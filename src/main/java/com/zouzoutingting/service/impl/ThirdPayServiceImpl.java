package com.zouzoutingting.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.serializer.ObjectArraySerializer;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.model.Order;
import com.zouzoutingting.service.IThirdPayService;
import com.zouzoutingting.utils.alipay.AliParamCore;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangyong on 16/8/21.
 */
@Service("thirdPayService")
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
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("app_id", Global.getAliAppID());
        requestMap.put("method", "alipay.trade.query");
        requestMap.put("charset", Global.ALI_PAY_INPUT_CHARSET);
        requestMap.put("sign_type", "RSA");
        requestMap.put("timestamp", simpleDateFormat.format(new Date()));
        requestMap.put("version",Global.ALI_PAY_INTERFACE_VERSION);
        Map<String, String> bizMap = new HashMap<String, String>();
        bizMap.put("out_trade_no", orderid+"");
        bizMap.put("trade_no", aliTradeid);
        requestMap.put("biz_content",JSONUtils.toJSONString(bizMap));

        String query_url = Global.getAliPayGatewayUrl() + AliParamCore.getFullUrlParam(requestMap);
        System.out.println(query_url);
        return  ispayed;
    }

    public static void main(String[] args){
        ThirdPayServiceImpl service = new ThirdPayServiceImpl();
        System.out.println(service.AliQueryTrade("11111", 123));
    }


}
