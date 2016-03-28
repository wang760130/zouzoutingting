package com.zouzoutingting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月27日
 */
@Controller
public class CityController extends BaseController {
	
	@RequestMapping({ "/list" })
	public void list() {
		System.out.println("list");
	}

}
