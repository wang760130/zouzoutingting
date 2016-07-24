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

import com.zouzoutingting.enums.SpotEnum;
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
    
    /**
     * 依据vid 获取 spot数据列表
     * @param request http请求servlet
     * @param response http返回servlet
     */
    @RequestMapping(value = "/spots", method = RequestMethod.POST)
    public void spots(HttpServletRequest request, HttpServletResponse response) {
        long vid = RequestParamUtil.getLongParam(request, "vid", -1L);
        
        try {
	        Map<String, Object> resultMap = new HashMap<String, Object>();
        	List<Spot> spotList = spotService.loadByViewID(vid);
	        
        	// 讲解
        	List<Map<String, Object>> explainList = new ArrayList<Map<String, Object>>();
        	// 厕所
        	List<Map<String, Object>> toiletList = new ArrayList<Map<String, Object>>();
        	// 拐点
        	List<Map<String, Object>> pointList = new ArrayList<Map<String, Object>>();
        	// 路线
        	List<Map<String, Object>> lineList = new ArrayList<Map<String, Object>>();
        	// 图片
        	List<Map<String, Object>> picList = new ArrayList<Map<String, Object>>();
        	
        	Map<String, Object> map = null;
	        for(Spot spot : spotList) {
	        	map = new HashMap<String, Object>();
	        	if(spot.getType() == SpotEnum.EXPLAIN.getType()) {
	        		map.put("id", spot.getId());
	        		map.put("name", spot.getName());
	        		map.put("vid",vid);
	        		map.put("sequence", spot.getSequence());
	        		map.put("pic", spot.getPic());
	        		map.put("audio", spot.getAudio());
	        		map.put("content", spot.getContent());
	        		String listPic = spot.getListPic();
	        		if(listPic != null && !"".equals(listPic)) {
	        			map.put("listpic", listPic.split(","));
	        		} else {
	        			map.put("listpic", NULL_ARRAY);
	        		}
	        		map.put("longitude", spot.getLongitude());
	        		map.put("latitude", spot.getLatitude());
	        		map.put("radius", spot.getRadius());
	        		map.put("isfree", spot.getIsfree());
	        		explainList.add(map);
	        	} else if(spot.getType() == SpotEnum.TOILET.getType()) {
	        		map.put("id", spot.getId());
	        		map.put("vid",vid);
	        		map.put("longitude", spot.getLongitude());
	        		map.put("latitude", spot.getLatitude());
	        		toiletList.add(map);
	        	} else if(spot.getType() == SpotEnum.POINT.getType()) {
	        		map.put("id", spot.getId());
	        		map.put("vid",vid);
	        		map.put("sequence", spot.getSequence());
	        		map.put("longitude", spot.getLongitude());
	        		map.put("latitude", spot.getLatitude());
	        		pointList.add(map);
	        	} else if(spot.getType() == SpotEnum.LINE.getType()) {
	        		map.put("id", spot.getId());
	        		map.put("vid",vid);
	        		map.put("sequence", spot.getSequence());
	        		map.put("longitude", spot.getLongitude());
	        		map.put("latitude", spot.getLatitude());
	        		lineList.add(map);
	        	} else if(spot.getType() == SpotEnum.PICTURE.getType()) {
	        		map.put("id", spot.getId());
	        		map.put("vid",vid);
	        		map.put("pic", spot.getPic());
	        	}
	        }
	        
    		resultMap.put("explain", explainList);
    		resultMap.put("toilet", toiletList);
    		resultMap.put("point", pointList);
    		resultMap.put("line", lineList);
	        resultMap.put("pic", picList);
	        
	        gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, resultMap, request, response);
	        return ;
        } catch (Exception e) {
        	logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
			return ;
		}
    }
}
