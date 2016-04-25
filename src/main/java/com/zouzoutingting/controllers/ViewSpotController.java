package com.zouzoutingting.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zouzoutingting.model.ViewSpot;
import com.zouzoutingting.service.IViewSpotService;
import com.zouzoutingting.utils.RequestParamUtil;

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
    @RequestMapping(value = "/viewspots", method = RequestMethod.POST)
    public void viewspots(HttpServletRequest request, HttpServletResponse response) {
        int cityid = RequestParamUtil.getIntegerParam(request, "cityid", -1);
        try {
        	List<ViewSpot> list = viewSpotService.getViewSpotByCity(cityid);
        	List<ViewSpot> resultList = null;
        	if(list != null && list.size() > 0) {
        		resultList = new ArrayList<ViewSpot>();
        		for(ViewSpot viewSpot : list) {
        			String listPic = viewSpot.getListPic();
        			if(listPic != null && !"".equals(listPic)) {
        				viewSpot.setPic(listPic.split(","));
        			}
        			resultList.add(viewSpot);
        		}
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, resultList, request, response);
			} else {
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_NULL, null, request, response);
			}
        } catch (Exception e) {
        	logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, null, request, response);
		}
    }

    /**
     * 景点详情
     * @param request http请求servlet
     * @param response http返回servlet
     */
    @RequestMapping(value = "/viewspot", method = RequestMethod.POST)
    public void viewspot(HttpServletRequest request, HttpServletResponse response){
        long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        try {
	        ViewSpot viewSpot = viewSpotService.getViewSpotByID(vid);
	        if(viewSpot != null) {
	        	String listPic = viewSpot.getListPic();
	        	if(listPic != null && !"".equals(listPic)) {
	        		viewSpot.setPic(listPic.split(","));
	        	}
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, viewSpot, request, response);
			} else {
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_NULL, null, request, response);
			}
        } catch (Exception e) {
        	logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, null, request, response);
		}
    }
}
