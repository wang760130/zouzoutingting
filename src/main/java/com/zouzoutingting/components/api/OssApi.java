package com.zouzoutingting.components.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.PutObjectResult;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月22日
 */
public class OssApi {
	
	private final static String END_POINT = "oss-cn-beijing.aliyuncs.com";
	private final static String ACCESS_KEY_ID = "nfWRH14XbUZL8xV3";
	private final static String ACCESS_KEY_SECRET = "YGmjR0KtRPRF4OwK25Y3jQbzUZ7GdJ";
	private final static String BUCKET_NAME = "zztt";
	
	
	public static OSSClient open() {
		// 创建ClientConfiguration实例，按照您的需要修改默认参数
		ClientConfiguration conf = new ClientConfiguration();
		// 开启支持CNAME选项
		conf.setSupportCname(true);
		// 创建OSSClient实例
		return new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET, conf);
	}
	
	/**
	 * 列举Bucket
	 * @return
	 */
	public static List<Bucket> listBuckets() {
		OSSClient ossClient = open();
		try {
			return ossClient.listBuckets();
		} finally {
			ossClient.shutdown();
		}
	}
	
	/**
	 * 上传
	 * @param fileName
	 * @param content
	 * @return
	 */
	public static PutObjectResult upload(String fileName, String content) {
		OSSClient ossClient = open();
		try {
			return ossClient.putObject(BUCKET_NAME, fileName, new ByteArrayInputStream(content.getBytes()));
		} finally {
			ossClient.shutdown();
		}
	}
	
	/**
	 * 上传
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public static PutObjectResult upload(String fileName, InputStream inputStream) {
		OSSClient ossClient = open();
		try {
			return ossClient.putObject(BUCKET_NAME, fileName, inputStream);
		} finally {
			ossClient.shutdown();
		}
	}
	
	/**
	 * 上传
	 * @param fileName
	 * @param file
	 * @return
	 */
	public static PutObjectResult upload(String fileName, File file) {
		OSSClient ossClient = open();
		try {
			return ossClient.putObject(BUCKET_NAME, fileName, file);
		} finally {
			ossClient.shutdown();
		}
	}

	/**
	 * 创建文件夹
	 * @param dir
	 * @return
	 */
	public static PutObjectResult mkdir(String dir) {
		OSSClient ossClient = open();
		try {
			return ossClient.putObject(BUCKET_NAME, dir, new ByteArrayInputStream(new byte[0]));
		} finally {
			ossClient.shutdown();
		}
	}
	
	
}	
