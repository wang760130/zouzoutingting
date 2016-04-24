package com.zouzoutingting.service;

import com.zouzoutingting.model.Spot;

import java.util.List;

/**
 * Created by zhangyong on 16/4/8.
 * 景点讲解节点服务层
 */
public interface ISpotService {
    public List<Spot> loadByViewIDAndType(long vid, int type);
    
    public List<Spot> loadByViewID(long vid);
}
