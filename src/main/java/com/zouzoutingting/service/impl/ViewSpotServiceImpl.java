package com.zouzoutingting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.ViewSpot;
import com.zouzoutingting.service.IViewSpotService;
import com.zouzoutingting.utils.Page;

/**
 * Created by zhangyong on 16/4/7.
 * viewspot服务层实现
 */
@Service("viewSpotService")
public class ViewSpotServiceImpl implements IViewSpotService {
    @Autowired
    private IDao<ViewSpot> viewSpotDao = null;

    @Override
    public List<ViewSpot> getViewSpotByCity(int cityID) {
        List<ViewSpot> list = null;
        if(cityID>0) {
            Page page = new Page();
            page.setCondition("cityid = " + cityID);
            page.setPageNo(1);
            page.setPageSize(100);

            list = viewSpotDao.page(page);
        }
        return list;
    }

    @Override
    public ViewSpot getViewSpotByID(long vid) {
        ViewSpot viewSpot = null;
        if(vid>0L) {
            viewSpot = viewSpotDao.load(vid);
        }
        return viewSpot;
    }
}
