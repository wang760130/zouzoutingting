package com.zouzoutingting.controllers;

import com.zouzoutingting.model.Spot;
import com.zouzoutingting.service.ISpotService;
import com.zouzoutingting.utils.RequestParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zhangyong on 16/4/8.
 * 景点讲解路线Controller
 */
@Controller
public class SpotController extends BaseController{

    @Autowired
    private ISpotService spotService;

    /**
     * 依据vid + type 获取 spot数据列表
     * @param request http请求servlet
     * @param response http返回servlet
     */
    @RequestMapping(value = "/spotList", method = RequestMethod.GET)
    public void spotList(HttpServletRequest request, HttpServletResponse response) {
        long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        int type = RequestParamUtil.getIntegerParam(request, "type", -1);

        List<Spot> list = spotService.loadByViewIDAndType(vid, type);
        if(list!=null && list.size()>0){
            gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, list, request, response);
        }else {
            gzipCipherResult(RETURN_CODE_SUCCESS, "无数据", list, request, response);
        }
    }
}
