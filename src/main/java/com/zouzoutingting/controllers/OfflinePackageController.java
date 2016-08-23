package com.zouzoutingting.controllers;

import java.beans.IntrospectionException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zouzoutingting.components.encrypt.MD5;
import com.zouzoutingting.enums.SpotEnum;
import com.zouzoutingting.model.Spot;
import com.zouzoutingting.model.ViewSpot;
import com.zouzoutingting.service.ISpotService;
import com.zouzoutingting.service.IViewSpotService;
import com.zouzoutingting.utils.BeanToMapUtil;
import com.zouzoutingting.utils.FileUtil;
import com.zouzoutingting.utils.HttpUtil;
import com.zouzoutingting.utils.OfflinePackageUtil;
import com.zouzoutingting.utils.ParamUtil;
import com.zouzoutingting.utils.ZipUtil;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年7月15日
 */
@Controller
public class OfflinePackageController extends BaseController {
	
	@Autowired
    private ISpotService spotService;
	
	@Autowired
	private IViewSpotService viewSpotService;
	
	@RequestMapping(value = "/offlinepackage/list", method = RequestMethod.GET)
	public ModelAndView offlinePackageList(Model model, HttpServletRequest request, HttpServletResponse response) {
		List<ViewSpot> viewSpotList = viewSpotService.getViewSpotList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("viewSpotList", viewSpotList);
		model.addAttribute("viewSpotList", viewSpotList);
		return new ModelAndView("offlinepackage", map);  
	}
	
	@RequestMapping(value = "/offlinepackage", method = RequestMethod.GET)
	public void offlinePackage(HttpServletRequest request, HttpServletResponse response) {
		long vid = ParamUtil.getLong(request, "vid", -1L);
		String zipFile = this.generate(vid);
		File file = new File(zipFile);
		
		try {
			this.downloadResult(file, response);
		} finally {
			if(file.exists()) {
				file.delete();
			}
		}
	}
	
	private String md5Url(String url) {
		String prefix = url.substring(url.lastIndexOf(".") + 1);
		return MD5.Md5Encryt(url) + "." + prefix;
	}
	
	private void writeToFile(String filePath, String data) {
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			File file = new File(filePath);
			if(!file.exists()) {
				file.createNewFile();
			}
			fileWriter = new FileWriter(file, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(data);
			bufferedWriter.flush();
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		} finally {
			if(bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					logger.info(e.getMessage(), e);
				}
			}
			
			if(fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}
	}
	
	private String generate(long vid) {
		String basePath = System.getProperty("user.dir") + File.separator;
		String tempPath = basePath + "offlinepackagetemp" + File.separator;
		String zipPath = basePath + "offlinepackage";

		String fileName = "viewspot_" + vid + "_" + System.currentTimeMillis();
		ViewSpot viewSpot = viewSpotService.getViewSpotByID(vid, 0);
     	List<Spot> spotList = spotService.loadByViewID(vid);
     	
     	try {
     		String defualtPic = viewSpot.getDefualtPic();
     		HttpUtil.downloadFile(tempPath + fileName + File.separator + this.md5Url(defualtPic), defualtPic);
     		
     		String listPic = viewSpot.getListPic();
     		if(listPic != null && !"".equals(listPic)) {
	     		String[] picArr = listPic.split(",");
	     		
	    		for(String pic : picArr) {
	    			HttpUtil.downloadFile(tempPath + fileName + File.separator + this.md5Url(pic), pic);
	    		}
	    		viewSpot.setPic(picArr);
     		} 
     		
     		Map<String, Object> viewSoptMap = new HashMap<String, Object>();
     		viewSoptMap = BeanToMapUtil.convertBeanToMap(viewSpot);
     		
     		Map<String, Object> spotMap = new HashMap<String, Object>();
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
	        		map.put("sequence", spot.getSequence());
	        		String pic = spot.getPic();
	        		HttpUtil.downloadFile(tempPath + fileName + File.separator + this.md5Url(pic), pic);
	        		map.put("pic", pic);
	        		String audio = spot.getAudio();
	        		audio = OfflinePackageUtil.decryptOffline(audio);
	        		HttpUtil.downloadFile(tempPath + fileName + File.separator + this.md5Url(audio), audio);
	        		map.put("audio", audio);
	        		map.put("content", spot.getContent());
	        		String spotListPic = spot.getListPic();
	        		if(spotListPic != null && !"".equals(spotListPic)) {
	        			String[] spotListPicArr = spotListPic.split(",");
	        			for(String spotpic : spotListPicArr) {
	            			HttpUtil.downloadFile(tempPath + fileName + File.separator + this.md5Url(spotpic), spotpic);
	            		}
	        			map.put("listpic", spotListPic.split(","));
	        		} else {
	        			map.put("listpic", new ArrayList<String>());
	        		}
	        		map.put("longitude", spot.getLongitude());
	        		map.put("latitude", spot.getLatitude());
	        		map.put("radius", spot.getRadius());
	        		map.put("isfree", spot.getIsfree());
	        		explainList.add(map);
	        	} else if(spot.getType() == SpotEnum.TOILET.getType()) {
	        		map.put("id", spot.getId());
	        		map.put("longitude", spot.getLongitude());
	        		map.put("latitude", spot.getLatitude());
	        		toiletList.add(map);
	        	} else if(spot.getType() == SpotEnum.POINT.getType()) {
	        		map.put("id", spot.getId());
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
	        		String pic = spot.getPic();
	        		HttpUtil.downloadFile(tempPath + fileName + File.separator + this.md5Url(pic), pic);
	        		map.put("pic", pic);
	        		picList.add(map);
	        	}
	        }
     		
	        spotMap.put("explain", explainList);
    		spotMap.put("toilet", toiletList);
    		spotMap.put("point", pointList);
    		spotMap.put("line", lineList);
    		spotMap.put("pic", picList);
    		
    		viewSoptMap.put("spot", spotMap);
    		
     		ObjectMapper mapper = new ObjectMapper();  
     		String json = mapper.writeValueAsString(viewSoptMap);
     		
     		String fileNamePath = tempPath + fileName + File.separator;
     		this.writeToFile(fileNamePath + "data.json", json);
     		ZipUtil.zip(fileNamePath, zipPath, fileName + ".zip");
     		
     		FileUtil.deleteDir(new File(fileNamePath));
     		
		} catch (IllegalArgumentException e) {
			logger.info(e.getMessage(), e);
		} catch (IntrospectionException e) {
			logger.info(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.info(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.info(e.getMessage(), e);
		} catch (JsonProcessingException e) {
			logger.info(e.getMessage(), e);
		} catch (ClientProtocolException e) {
			logger.info(e.getMessage(), e);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
     	return zipPath + File.separator + fileName + ".zip";
	}
	
}
