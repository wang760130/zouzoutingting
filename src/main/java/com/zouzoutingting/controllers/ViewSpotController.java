package com.zouzoutingting.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zouzoutingting.model.City;
import com.zouzoutingting.model.ViewSpot;
import com.zouzoutingting.service.ICityService;
import com.zouzoutingting.service.IViewSpotService;
import com.zouzoutingting.utils.DistanceUtil;
import com.zouzoutingting.utils.RequestParamUtil;

/**
 * Created by zhangyong on 16/4/5.
 * 景点接口
 */
@Controller
public class ViewSpotController extends BaseController {

    @Autowired
    private IViewSpotService viewSpotService;
    
    @Autowired
	private ICityService cityService;
    
    /**
     * 景点列表
     * @param request 请求参数
     * @param response 返回
     */
    @RequestMapping(value = "/viewspots", method = RequestMethod.POST)
    public void viewspots(HttpServletRequest request, HttpServletResponse response) {
        int cityid = RequestParamUtil.getIntegerParam(request, "cityid", -1);
        final double lon = RequestParamUtil.getDoubleParam(request, "lon", 0.0d);
        final double lat = RequestParamUtil.getDoubleParam(request, "lat", 0.0d);
		
        try {
        	List<ViewSpot> list = viewSpotService.getViewSpotByCity(cityid);
        	if(list != null && list.size() > 0) {
        		
        		if(lon != 0.0d && lat != 0.0d) {
        			// 经纬度不为空时，增加距离字段，并且按距离排序
	        		for(ViewSpot viewSpot : list) {
	        			double distance = calculateDistance(viewSpot, lon, lat);
	        			viewSpot.setDistance(distance);
	        		}
        			
        			Collections.sort(list, new Comparator<ViewSpot>() {

						@Override
						public int compare(ViewSpot viewSpot1, ViewSpot viewSpot2) {
							double distance1 = viewSpot1.getDistance();
							double distance2 = viewSpot2.getDistance();
							return (int)(distance1 - distance2);
						}
        			});
        		}
        		
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, list, request, response);
			} else {
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_NULL, NULL_ARRAY, request, response);
			}
        } catch (Exception e) {
        	logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_ARRAY, request, response);
		}
    }

    
    /**
	 * 城市详情 + 景点列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/cityspot", method = RequestMethod.POST)
	public void citySpot(HttpServletRequest request, HttpServletResponse response) {
		int cityid = RequestParamUtil.getIntegerParam(request, "id", 1);
		final double lon = RequestParamUtil.getDoubleParam(request, "lon", 0.0d);
        final double lat = RequestParamUtil.getDoubleParam(request, "lat", 0.0d);
        
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			City city = cityService.load(cityid);
			List<ViewSpot> viewSpotList = viewSpotService.getViewSpotByCity(cityid);
			
			if(viewSpotList != null && viewSpotList.size() > 0) {
        		
        		if(lon != 0.0d && lat != 0.0d) {
        			// 经纬度不为空时，增加距离字段，并且按距离排序
	        		for(ViewSpot viewSpot : viewSpotList) {
	        			double distance = calculateDistance(viewSpot, lon, lat);
	        			viewSpot.setDistance(distance);
	        		}
        			
        			Collections.sort(viewSpotList, new Comparator<ViewSpot>() {

						@Override
						public int compare(ViewSpot viewSpot1, ViewSpot viewSpot2) {
							double distance1 = viewSpot1.getDistance();
							double distance2 = viewSpot2.getDistance();
							return (int)(distance1 - distance2);
						}
        			});
        		}
			}
			map.put("id", city.getId());
			map.put("name", city.getName());
			map.put("ename",city.getEname());
			map.put("pic", city.getPic());
			map.put("synopsis", city.getSynopsis());
			map.put("viewspot", viewSpotList);
			gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, map, request, response);
		} catch (Exception e) {
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
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
        double lon = RequestParamUtil.getDoubleParam(request, "lon", 0.0d);
        double lat = RequestParamUtil.getDoubleParam(request, "lat", 0.0d);
        
        try {
	        ViewSpot viewSpot = viewSpotService.getViewSpotByID(vid);
	        if(viewSpot != null) {
	        	if(lon != 0.0d && lat != 0.0d) {
	        		double distance = calculateDistance(viewSpot, lon, lat);
	        		viewSpot.setDistance(distance);
	        	}
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, viewSpot, request, response);
			} else {
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_NULL, NULL_OBJECT, request, response);
			}
        } catch (Exception e) {
        	logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
		}
    }
    
    private double calculateDistance(ViewSpot viewSpot, double lon, double lat) {
    	String centercoord = viewSpot.getCentercoord();
    	if(centercoord != null && !centercoord.equals("")) {
    		String[] centercoords = centercoord.split(",");
    		if(centercoords != null && centercoords.length == 2) {
    			double viewSpotLon = Double.valueOf(centercoords[0]);
    			double viewSpotLat = Double.valueOf(centercoords[1]);
    			return DistanceUtil.getDistance(viewSpotLon, viewSpotLat, lon, lat);
    		}
    	}
    	return 0.0d;
    }
}
