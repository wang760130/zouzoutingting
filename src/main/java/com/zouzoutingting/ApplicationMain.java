package com.zouzoutingting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 启动Spring Boot项目的唯一入口
 * 
 * @author Wangjiajun
 * @Email wangjiajun@58.com
 * @date 2016年8月24日
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationMain {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApplicationMain.class, args);
	}
}
