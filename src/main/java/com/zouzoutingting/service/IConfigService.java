package com.zouzoutingting.service;

import com.zouzoutingting.model.Config;

/**
 * Created by zhangyong on 17/2/4.
 * 配置获取服务层
 */
public interface IConfigService {
    public String getCoinfig(String key) throws Exception;
}
