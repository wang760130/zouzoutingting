package com.zouzoutingting.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zouzoutingting.model.Spot;
import com.zouzoutingting.service.ISpotService;
import com.zouzoutingting.utils.RequestParamUtil;

/**
 * Created by zhangyong on 16/4/8.
 * 景点讲解路线Controller
 */
@Controller
public class SpotController extends BaseController{

    @Autowired
    private ISpotService spotService;
    
    private static final int EXPLAIN = 0;
    private static final int TOILET = 1;
    private static final int POINT = 2;
    
    /**
     * 依据vid + type 获取 spot数据列表
     * @param request http请求servlet
     * @param response http返回servlet
     */
    @RequestMapping(value = "/spots", method = RequestMethod.POST)
    public void spots(HttpServletRequest request, HttpServletResponse response) {
        long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        //  类型  0:讲解  1:厕所  2 拐点
        int type = RequestParamUtil.getIntegerParam(request, "type", -1);
        
        try {
	        Map<String, Object> resultMap = new HashMap<String, Object>();
        	List<Spot> list = spotService.loadByViewID(vid);
	        
        	// 讲解
        	List<Map<String, Object>> explainList = new ArrayList<Map<String, Object>>();
        	// 厕所
        	List<Map<String, Object>> toiletList = new ArrayList<Map<String, Object>>();
        	// 拐点
        	List<Map<String, Object>> pointList = new ArrayList<Map<String, Object>>();
        	
        	Map<String, Object> map = null;
	        for(Spot spot : list) {
	        	map = new HashMap<String, Object>();
	        	if(spot.getType() == EXPLAIN) {
	        		map.put("id", spot.getId());
	        		map.put("name", spot.getName());
	        		map.put("sequence", spot.getSequence());
	        		map.put("pic", spot.getPic());
	        		map.put("audio", spot.getAudio());
	        		map.put("longitude", spot.getLongitude());
	        		map.put("latitude", spot.getLatitude());
	        		map.put("radius", spot.getRadius());
	        		map.put("isfree", spot.getIsfree());
	        		explainList.add(map);
	        	} else if(spot.getType() == TOILET) {
	        		map.put("id", spot.getId());
	        		map.put("longitude", spot.getLongitude());
	        		map.put("latitude", spot.getLatitude());
	        		toiletList.add(map);
	        	} else if(spot.getType() == POINT) {
	        		map.put("id", spot.getId());
	        		map.put("sequence", spot.getSequence());
	        		map.put("longitude", spot.getLongitude());
	        		map.put("latitude", spot.getLatitude());
	        		pointList.add(map);
	        	} 
	        }
	        
	        if(type == EXPLAIN) {
	        	resultMap.put("explain", explainList);
        	} else if(type == TOILET) {
        		resultMap.put("toilet", toiletList);
        	} else if(type == POINT) {
        		resultMap.put("point", pointList);
        	} else {
        		resultMap.put("explain", explainList);
        		resultMap.put("toilet", toiletList);
        		resultMap.put("point", pointList);
        	}
	        
	        gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, resultMap, request, response);
	       
        } catch (Exception e) {
        	logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, null, request, response);
		}
    }
}
