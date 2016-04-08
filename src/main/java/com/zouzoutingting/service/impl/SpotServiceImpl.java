package com.zouzoutingting.service.impl;

import com.zouzoutingting.dao.IDao;
import com.zouzoutingting.model.Spot;
import com.zouzoutingting.service.ISpotService;
import com.zouzoutingting.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(vid>0L && type >0) {
            Page page = new Page();
            page.setCondition("vid = " + vid + " and type=" + type +" and state=1");
            page.setPageNo(1);
            page.setPageSize(100);
            page.setOrderName("sequence asc");

            list = spotDao.page(page);
        }
        return list;
    }
}
