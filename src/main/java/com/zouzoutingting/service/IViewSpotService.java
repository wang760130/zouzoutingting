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
     * @param uid 用户id
     * @return list
     */
    public List<ViewSpot> getViewSpotByCity(int cityID, long uid);

    /**
     * 通过id命中景点
     * @param vid 景点id
     * @param uid 用户id
     * @return viewspot
     */
    public ViewSpot getViewSpotByID(long vid, long uid);

    /**
     * 获取景点列表
     * @return
     */
    public List<ViewSpot> getViewSpotList();
}
	