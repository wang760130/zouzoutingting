package com.zouzoutingting.service;

import com.zouzoutingting.model.ViewSpot;

import java.util.List;

/**
 * Created by zhangyong on 16/4/5.
 * 景点逻辑层
 */
public interface IViewSpotService {
    /**
     * 一句城市获取景点列表
     * @param cityID 城市id
     * @return list
     */
    public List<ViewSpot> getViewSpotByCity(int cityID);

    /**
     * 通过id命中景点
     * @param vid 景点id
     * @return viewspot
     */
    public ViewSpot getViewSpotByID(long vid);
}