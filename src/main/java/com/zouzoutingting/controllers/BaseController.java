package com.zouzoutingting.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.utils.DES;
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
	
	public static final String RETURN_MESSAGE_SUCCESS = "成功";
	public static final String RETURN_MESSAGE_EXCEPTION = "服务器端异常";
	public static final String RETURN_MESSAGE_NULL = "数据为空";
	public static final String RETUEN_MESSAGE_PARAMETER_ERROR = "参数错误";
	
	public static final Map<String, String> NULL_OBJECT = new HashMap<String, String>();
	public static final List<String> NULL_ARRAY = new ArrayList<String>();
	
	public void gzipCipherResult(int returnCode, String returnMessage, Object entity, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", returnCode);
		map.put("message", returnMessage);
		map.put("entity", entity == null ? new HashMap<String, String>() : entity);
		map.put("token",""); // 返回用户登录凭证
		
        ObjectMapper mapper = new ObjectMapper();  
        String content = null;
        byte[] result = null;
        try {
			content = mapper.writeValueAsString(map);
			request.setAttribute(Global.RESULT_CONTENT, content);
			result = GZipUtils.compress(content.getBytes("UTF-8"));
			result = DES.encrypt(result, Global.DESKEY);
			
			response.setContentType ("application/octet-stream");
			response.getOutputStream().write(result);
			response.getOutputStream().flush();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}  
	}
	
}
