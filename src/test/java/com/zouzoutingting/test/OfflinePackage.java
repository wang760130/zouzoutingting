package com.zouzoutingting.test;

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
import java.util.zip.ZipFile;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zouzoutingting.model.Spot;
import com.zouzoutingting.model.ViewSpot;
import com.zouzoutingting.service.ISpotService;
import com.zouzoutingting.service.IViewSpotService;
import com.zouzoutingting.utils.BeanToMapUtil;
import com.zouzoutingting.utils.HttpUtil;
import com.zouzoutingting.utils.MD5;
import com.zouzoutingting.utils.ZipUtil;

/**
 * 景点离线包生成
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月10日
 */

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class OfflinePackage {
	
	@Autowired
    private ISpotService spotService;
	
	@Autowired
	private IViewSpotService viewSpotService;
	 
	// 大唐芙蓉园 景点ID
	private static final long DTFRY_VIEW_SPOT_ID = 12L;
	
	// 华清池 景点ID
	private static final long HQC_VIEW_SPOT_ID = 13L;
	
	// 袁家村
	private static final long YJC_VIEW_SPOT_ID = 20L;
	
	private static final int EXPLAIN = 0;
	private static final int TOILET = 1;
	private static final int POINT = 2;
	private static final int LINE = 3;		// 路线
	
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
			e.printStackTrace();
		} finally {
			if(bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void generate(long vid) {
		String basePath = System.getProperty("user.dir") + File.separator;
		String tempPath = basePath + "offlinepackagetemp" + File.separator;
		String zipPath = basePath + "offlinepackage";

		String fileName = "viewspot_" + vid + "_" + System.currentTimeMillis();
		ViewSpot viewSpot = viewSpotService.getViewSpotByID(vid);
     	List<Spot> spotList = spotService.loadByViewID(vid);
     	
     	try {
     		String defualtPic = viewSpot.getDefualtPic();
     		String listPic = viewSpot.getListPic();
     		String[] picArr = listPic.split(",");
     		
     		String defualtPicFile = this.md5Url(defualtPic);
     		HttpUtil.downloadFile(tempPath + fileName + File.separator + defualtPicFile, defualtPic);
     		
    		for(String pic : picArr) {
    			String picFile = this.md5Url(pic);
    			HttpUtil.downloadFile(tempPath + fileName + File.separator + picFile, pic);
    		}
     		
    		viewSpot.setPic(picArr);
     		
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
        	
        	Map<String, Object> map = null;
	        for(Spot spot : spotList) {
	        	map = new HashMap<String, Object>();
	        	if(spot.getType() == EXPLAIN) {
	        		map.put("id", spot.getId());
	        		map.put("name", spot.getName());
	        		map.put("sequence", spot.getSequence());
	        		String pic = spot.getPic();
	        		String picFile = this.md5Url(pic);
	        		HttpUtil.downloadFile(tempPath + fileName + File.separator + picFile, pic);
	        		map.put("pic", pic);
	        		String audio = spot.getAudio();
	        		String audioFile = this.md5Url(audio);
	        		HttpUtil.downloadFile(tempPath + fileName + File.separator + audioFile, audio);
	        		map.put("audio", audio);
	        		map.put("content", spot.getContent());
	        		String spotListPic = spot.getListPic();
	        		if(spotListPic != null && !"".equals(spotListPic)) {
	        			map.put("listpic", spotListPic.split(","));
	        		} else {
	        			map.put("listpic", new ArrayList<String>());
	        		}
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
	        	} else if(spot.getType() == LINE) {
	        		map.put("id", spot.getId());
	        		map.put("vid",vid);
	        		map.put("sequence", spot.getSequence());
	        		map.put("longitude", spot.getLongitude());
	        		map.put("latitude", spot.getLatitude());
	        		lineList.add(map);
	        	}
	        }
     		
	        spotMap.put("explain", explainList);
    		spotMap.put("toilet", toiletList);
    		spotMap.put("point", pointList);
    		spotMap.put("line", lineList);
    		
    		viewSoptMap.put("spot", spotMap);
    		
     		ObjectMapper mapper = new ObjectMapper();  
     		String json = mapper.writeValueAsString(viewSoptMap);
     		
     		System.out.println(json);
     		this.writeToFile(tempPath + fileName + File.separator + "data.json", json);
     		
     		ZipUtil.zip(tempPath + fileName + File.separator, zipPath, fileName + ".zip");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void generate() {
		this.generate(YJC_VIEW_SPOT_ID);
	}
	
}
