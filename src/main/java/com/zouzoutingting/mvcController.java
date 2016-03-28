package com.zouzoutingting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvc")
public class mvcController {
 
    @RequestMapping("/hello")
    public String hello(){        
    	System.out.println("hello");
        return "hello";
    }
}
