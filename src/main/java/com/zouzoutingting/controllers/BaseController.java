package com.zouzoutingting.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.utils.DESCipher;
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
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public void gzipCipherResult(boolean isSuccess, String returnMessage, Object entity, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", isSuccess);
		map.put("message", returnMessage);
		map.put("entity", entity);
		
        ObjectMapper mapper = new ObjectMapper();  
        String content = null;
        byte[] result = null;
        try {
			content = mapper.writeValueAsString(map);
			result = GZipUtils.compress(content.getBytes("UTF-8"));
			result = DESCipher.encrypt(result, Global.RESPONSE_DESKEY);
			
			response.setContentType ("application/octet-stream");
			response.getOutputStream().write(result);
			response.getOutputStream().flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}  
        
        Object requestid = request.getAttribute("requestid");
		if(requestid != null) {
			String requestId = requestid.toString();
			long currentTime = System.currentTimeMillis();
			long timeMillis = Long.valueOf(requestId.substring(0, requestId.length() - 3));
			long executeTime = currentTime - timeMillis;
			logger.info("requestid="+requestId+",  executeTime="+executeTime+"ms, result=" + content);
		}
        
	}
	
}
