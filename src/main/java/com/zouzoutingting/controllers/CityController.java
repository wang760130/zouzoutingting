package com.zouzoutingting.controllers;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zouzoutingting.service.ICityService;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月27日
 */
@Controller
@RequestMapping("/city")
public class CityController extends BaseController {
	
	@Autowired
	private ICityService cityService;
	 
	@RequestMapping({ "/list" })
	public void list(PrintWriter pw) {
		System.out.println("list");
		pw.write("list");  
	}

}
