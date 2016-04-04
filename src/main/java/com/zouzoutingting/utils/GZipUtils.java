package com.zouzoutingting.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 数据压缩gzip
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月4日
 */
public class GZipUtils {
	
	/**
	 * 数据压缩
	 * @param data byte数组
	 * @return byte数组
	 * @throws Exception
	 */
	public static byte[] compress(byte[] data) throws Exception {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		GZIPOutputStream out = new GZIPOutputStream(bytes);
		out.write(data);
		out.finish();
		return bytes.toByteArray();
	}

	/**
	 * 数据解压
	 * @param data byte数组
	 * @return byte数组
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] compressedData)
			throws Exception {
		return decompress(new ByteArrayInputStream(compressedData));
	}
	
	/**
	 * 数据解压
	 * @param inputdata 输入流
	 * @return byte数组
	 * @throws Exception
	 */
	public static byte[] decompress(InputStream inputdata) throws Exception {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		GZIPInputStream in = new GZIPInputStream(inputdata);
		int count;
		byte data[] = new byte[1024];
		while ((count = in.read(data, 0, 1024)) != -1) {
			bytes.write(data, 0, count);
		}
		in.close();
		return bytes.toByteArray();
	}
}
