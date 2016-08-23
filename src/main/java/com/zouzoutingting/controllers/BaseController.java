package com.zouzoutingting.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.components.encrypt.DES;
import com.zouzoutingting.utils.GZipUtils;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月23日
 */


/**
 *                            _ooOoo_
 *                           o8888888o
 *                           88" . "88
 *                           (| -_- |)
 *                            O\ = /O
 *                        ____/`---'\____
 *                      .   ' \\| |// `.
 *                       / \\||| : |||// \
 *                     / _||||| -:- |||||- \
 *                       | | \\\ - /// | |
 *                     | \_| ''\---/'' | |
 *                      \ .-\__ `-` ___/-. /
 *                   ___`. .' /--.--\ `. . __
 *                ."" '< `.___\_<|>_/___.' >'"".
 *               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *                 \ \ `-. \_ __\ /__ _/ .-` / /
 *         ======`-.____`-.___\_____/___.-`____.-'======
 *                            `=---='
 *
 *         .............................................
 *                  佛祖保佑             永无BUG
 *          佛曰:
 *                  写字楼里写字间，写字间里程序员；
 *                  程序人员写程序，又拿程序换酒钱。
 *                  酒醒只在网上坐，酒醉还来网下眠；
 *                  酒醉酒醒日复日，网上网下年复年。
 *                  但愿老死电脑间，不愿鞠躬老板前；
 *                  奔驰宝马贵者趣，公交自行程序员。
 *                  别人笑我忒疯癫，我笑自己命太贱；
 *                  不见满街漂亮妹，哪个归得程序员？
 *     ----------------------听说java继承会继承父类的所有属性→_→---------------------------
 */
public class BaseController {
	
	public Logger logger = Logger.getLogger(this.getClass());

	// 返回成功唯一标识
	public static final int RETURN_CODE_SUCCESS = 0;
	// 主要用作服务器异常
	public static final int RETURN_CODE_EXCEPTION = -1;
	// 主要用作接收数据参数异常
	public static final int RETURN_CODE_PARAMETER_ERROR = -2;
	// 主要用于用户信息验证失败
	public static final int RETURN_CODE_TOKEN_ERROR = -3;
	
	public static final String RETURN_MESSAGE_SUCCESS = "成功";
	public static final String RETURN_MESSAGE_EXCEPTION = "服务器端异常";
	public static final String RETURN_MESSAGE_NULL = "数据为空";
	public static final String RETUEN_MESSAGE_PARAMETER_ERROR = "参数错误";
	public static final String RETURN_MESSAGE_TOKEN_ERROR = "用户信息验证失败";
	
	public static final Map<String, String> NULL_OBJECT = new HashMap<String, String>();
	public static final List<String> NULL_ARRAY = new ArrayList<String>();
	
	/**
	 * 对App返回数据，将结果gzip压缩，des加密，并使用IO流的方式输出结果。
	 * @param returnCode
	 * @param returnMessage
	 * @param entity
	 * @param request
	 * @param response
	 */
	public void gzipCipherResult(int returnCode, String returnMessage, Object entity, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", returnCode);
		map.put("message", returnMessage);
		map.put("entity", entity == null ? new HashMap<String, String>() : entity);
		// 不返回用户凭证
//		map.put("token", request.getAttribute("token")); // 返回用户登录凭证
		
		ServletOutputStream outputStream = null;
        ObjectMapper mapper = new ObjectMapper();  
        String content = null;
        byte[] result = null;
        try {
			content = mapper.writeValueAsString(map);
			request.setAttribute(Global.RESULT_CONTENT, content);
			result = GZipUtils.compress(content.getBytes(Global.DEFUALT_CHARSET));
			result = DES.encrypt(result, Global.DESKEY);
			
			outputStream = response.getOutputStream();
			response.setContentType ("application/octet-stream");
			outputStream.write(result);
			outputStream.flush();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * JSON统一回调
	 * @param isSuccess
	 * @param returnMessage
	 * @param entity
	 * @param response
	 */
	public void jsonResult(boolean isSuccess, String returnMessage, Object entity, HttpServletResponse response) {
		this.jsonResult(isSuccess, returnMessage, entity, "", response);
	}
	
	/**
	 * JSON统一回调
	 * @param isSuccess
	 * @param returnMessage
	 * @param callback
	 * @param entity
	 * @param response
	 */
	public void jsonResult(boolean isSuccess, String returnMessage, Object entity, String callback, HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			if(entity == null)
				entity = new HashMap<String,Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", isSuccess);
			map.put("message", returnMessage);
			map.put("entity", entity);
			JSONObject jsonObject = JSONObject.fromObject(map);  
			String jsonStr = jsonObject.toString();
			
			if(StringUtils.isNotBlank(callback)) {
				jsonStr = callback + "(" + jsonStr + ")";
            } 
			
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			printWriter = response.getWriter();
			printWriter.print(jsonStr);
			printWriter.flush();
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		} finally {
			if(printWriter != null) {
				printWriter.close();
			}
		}
	}

	/**
	 * 返回字符串
	 * @param data 返回数据
	 * @param response
     */
	public void stringResult(String data, HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			if(data == null){
				data = "";
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain;charset=\"UTF-8\"");
			printWriter = response.getWriter();
			printWriter.print(data);
			printWriter.flush();
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		} finally {
			if(printWriter != null) {
				printWriter.close();
			}
		}

	}
	
	/**
	 * 文件下载
	 * @param file
	 * @param response
	 */
	public void downloadResult(File file, HttpServletResponse response) {
		InputStream is = null;
		ServletOutputStream outputStream = null;
		try {
			if(file != null && file.exists()) {
				outputStream = response.getOutputStream();
				String filename = URLEncoder.encode(file.getName(), "utf-8");
				
				response.reset();
	            response.setContentType("application/x-msdownload");
	            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	            
	            long length = file.length();
	            if(length != 0) {
	                is = new FileInputStream(file);
	                byte[] buf = new byte[4096];
	                int readLength;
	                while (((readLength = is.read(buf)) != -1)) {
	                	outputStream.write(buf, 0, readLength);
	                }
	                outputStream.flush();
	            }
	 		}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.info(e.getMessage(), e);
				}
			} 
			
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.info(e.getMessage(), e);
				}
			}
			
		}
	}
	
	/**
	 * 显示图片
	 * @param file
	 * @param response
	 */
	public void imgaeResult(File file, HttpServletResponse response) {
		FileInputStream fis = null;
		ServletOutputStream outputStream = null;
		try {
			
			if(file != null && file.exists()) {
				
				response.reset();
				response.setContentType("image/jpeg");
				outputStream = response.getOutputStream();
				
				fis = new FileInputStream(file.getName());
				byte[] buf = new byte[fis.available()];  
				fis.read(buf);  
				outputStream.write(buf); 
				outputStream.flush();
			}
		} catch (FileNotFoundException e) {
			logger.info(e.getMessage(), e);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		} finally {
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.info(e.getMessage(), e);
				}
			}	
			
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}
	}
	
}
