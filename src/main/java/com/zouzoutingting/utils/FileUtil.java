package com.zouzoutingting.utils;

import java.io.File;

/**
 * @author Jerry Wang
 * @Email jerry002@126.com
 * @date 2016年8月18日
 */
public class FileUtil {

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
}
