package com.zouzoutingting.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zouzoutingting.enums.OrderStateEnum;
import com.zouzoutingting.model.Order;
import com.zouzoutingting.service.IOrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.ViewSpot;
import com.zouzoutingting.service.IViewSpotService;

/**
 * Created by zhangyong on 16/4/7.
 * viewspot服务层实现
 */
@Service("viewSpotService")
public class ViewSpotServiceImpl implements IViewSpotService {
    private static final Logger logger = Logger.getLogger(ViewSpotServiceImpl.class);

    @Autowired
    private IDao<ViewSpot> viewSpotDao = null;

    @Override
    public List<ViewSpot> getViewSpotByCity(int cityID, long uid) {
        List<ViewSpot> list = null;
        List<ViewSpot> resultList = null;
        if(cityID > 0) {
            String condition = "cityid = " + cityID + " and state = 1";
            list = viewSpotDao.list(condition, null);
        }
        
        if(list != null && list.size() > 0) {
        	resultList = new ArrayList<ViewSpot>();
            IOrderService orderService = new OrderServiceImpl();
    		for(ViewSpot viewSpot : list) {
    			String listPic = viewSpot.getListPic();
    			if(listPic != null && !"".equals(listPic)) {
    				viewSpot.setPic(listPic.split(","));
    			}
                //处理是否支付逻辑
                if(uid>0 && viewSpot.getPrice()>0.0){
                    Order order = orderService.getOrderByUidAndVid(uid, viewSpot.getId());
                    if(order!=null && order.getState()!= OrderStateEnum.Finish.getState()){
                        viewSpot.setOfflinepackage("");
                        logger.info("uid:"+uid+" vid:"+viewSpot.getId() +" not payed");
                    }
                }
    			resultList.add(viewSpot);
    		}
        }
        
        return resultList;
    }

    @Override
    public ViewSpot getViewSpotByID(long vid) {
        ViewSpot viewSpot = null;
        if(vid > 0L) {
            viewSpot = viewSpotDao.load(vid);
        }
        
        if(viewSpot != null) {
        	String listPic = viewSpot.getListPic();
        	if(listPic != null && !"".equals(listPic)) {
        		viewSpot.setPic(listPic.split(","));
        	}
        }
        
        return viewSpot;
    }
}
