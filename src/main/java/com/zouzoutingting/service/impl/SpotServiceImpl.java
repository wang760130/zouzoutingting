package com.zouzoutingting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.model.Spot;
import com.zouzoutingting.service.ISpotService;
import com.zouzoutingting.utils.OfflinePackageUtil;

/**
 * Created by zhangyong on 16/4/8.
 * 景点讲解逻辑层实现
 */
@Service("spotService")
public class SpotServiceImpl implements ISpotService {
    @Autowired
    private IDao<Spot> spotDao = null;

    @Override
    public List<Spot> loadByViewIDAndType(long vid, int type) {
        List<Spot> list = null;
        if(vid > 0L && type >= 0) {
        	String condition = "vid = " + vid + " and type=" + type +" and state = 1";
        	String orderName = "sequence asc";
            list = spotDao.list(condition, orderName);
            if(list != null && list.size() > 0) {
            	for(Spot spot : list) {
            		spot.setAudio(OfflinePackageUtil.generateOffline(spot.getAudio()));
            	}
            }
        }
        return list;
    }
    
    @Override
	public List<Spot> loadByViewID(long vid) {
		List<Spot> list = null;
		if (vid > 0L) {
			String condition = "vid = " + vid + " and state = 1";
			String orderName = "sequence asc";
			list = spotDao.list(condition, orderName);
			if(list != null && list.size() > 0) {
            	for(Spot spot : list) {
            		spot.setAudio(OfflinePackageUtil.generateOffline(spot.getAudio()));
            	}
            }
		}
		return list;
	}
}
