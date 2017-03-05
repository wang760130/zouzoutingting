package com.zouzoutingting.service.impl;

import com.zouzoutingting.components.dao.IDao;
import com.zouzoutingting.components.dao.Page;
import com.zouzoutingting.model.Config;
import com.zouzoutingting.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangyong on 17/2/4.
 */
@Service("configService")
public class ConfigServiceImpl implements IConfigService{
    @Autowired
    private IDao<Config> configDao = null;

    @Override
    public String getCoinfig(String key) throws Exception {
        String config = null;
        String condition = "kname = '" + key+"'";
        Page page = new Page();
        page.setCondition(condition);
        page.setPageNo(1);
        page.setPageSize(1);
        List<Config> list = configDao.page(page);
        if(list!=null && list.size()>0){
            config = list.get(0).getVal();
        }
        return config;
    }
}
