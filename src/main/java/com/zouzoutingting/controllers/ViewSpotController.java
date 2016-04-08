package com.zouzoutingting.controllers;

import com.zouzoutingting.model.ViewSpot;
import com.zouzoutingting.service.IViewSpotService;
import com.zouzoutingting.utils.RequestParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zhangyong on 16/4/5.
 * 景点接口
 */
@Controller
public class ViewSpotController extends BaseController {

    @Autowired
    private IViewSpotService viewSpotService;
    /**
     * 景点列表
     * @param request 请求参数
     * @param response 返回
     */
    @RequestMapping(value = "/viewspotList", method = RequestMethod.GET)
    public void viewSpotList(HttpServletRequest request, HttpServletResponse response) {
        int cityid = RequestParamUtil.getIntegerParam(request, "cityid", -1);
        //坐标+imei
        List<ViewSpot> list = viewSpotService.getViewSpotByCity(cityid);
        gzipCipherResult(true, "获取成功", list, request, response);
    }

    /**
     * 景点详情
     * @param request http请求servlet
     * @param response http返回servlet
     */
    @RequestMapping(value = "/viewspotDetail", method = RequestMethod.GET)
    public void viewSpotDetail(HttpServletRequest request, HttpServletResponse response){
        long detailID = RequestParamUtil.getLongParam(request, "vid", -1L);
        ViewSpot spot = viewSpotService.getViewSpotByID(detailID);
        gzipCipherResult(true, "获取成功", spot, request, response);
    }
}
