package com.zouzoutingting.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zouzoutingting.enums.OrderStateEnum;
import com.zouzoutingting.model.Order;
import com.zouzoutingting.utils.OfflinePackageUtil;
import com.zouzoutingting.utils.Page;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.ViewSpot;
import com.zouzoutingting.service.IOrderService;
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
    @Autowired
    private IDao<Order> orderDao = null;

    @Autowired
    private IOrderService orderService;
    
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
    		for(ViewSpot viewSpot : list) {
    			
    			viewSpot.setOfflinepackage(OfflinePackageUtil.generateOffline(viewSpot.getOfflinepackage()));
    			
    			String listPic = viewSpot.getListPic();
    			if(listPic != null && !"".equals(listPic)) {
    				viewSpot.setPic(listPic.split(","));
    			}
                //处理是否支付逻辑
                if(viewSpot.getPrice()>0.0){
                    if(uid>0L) {
                        Order order = orderService.getOrderByUidAndVid(uid, viewSpot.getId());
                        if (!(order != null && order.getState() == OrderStateEnum.Finish.getState())) {
                            viewSpot.setIspayed(false);
                            logger.info("uid:" + uid + " vid:" + viewSpot.getId() + " not payed");
                        }
                    }else{
                       viewSpot.setIspayed(false);
                    }
                }
    			resultList.add(viewSpot);
    		}
        }
        
        return resultList;
    }

    @Override
    public ViewSpot getViewSpotByID(long vid, long uid) {
        ViewSpot viewSpot = null;
        if(vid > 0L) {
            viewSpot = viewSpotDao.load(vid);
        }
        
        if(viewSpot != null) {
        	
        	viewSpot.setOfflinepackage(OfflinePackageUtil.generateOffline(viewSpot.getOfflinepackage()));
        	
        	String listPic = viewSpot.getListPic();
        	if(listPic != null && !"".equals(listPic)) {
        		viewSpot.setPic(listPic.split(","));
        	}
        	
        	//处理是否支付逻辑
            if(viewSpot.getPrice() > 0.0){
                if(uid > 0L) {
                    Order order = orderService.getOrderByUidAndVid(uid, viewSpot.getId());
                    if (!(order != null && order.getState() == OrderStateEnum.Finish.getState())) {
                        viewSpot.setIspayed(false);
                        logger.info("uid:" + uid + " vid:" + viewSpot.getId() + " not payed");
                    }
                }else{
                   viewSpot.setIspayed(false);
                }
            }
        }
        
        return viewSpot;
    }
    
    @Override
    public List<ViewSpot> getViewSpotList() {
    	String condition = "state = 1";
        List<ViewSpot> list =  viewSpotDao.list(condition, "id asc");
        if(list != null && list.size() > 0) {
        	for(ViewSpot viewSpot : list) {
        		 viewSpot.setOfflinepackage(OfflinePackageUtil.generateOffline(viewSpot.getOfflinepackage()));
        	}
        }
        return list;
    }
}
