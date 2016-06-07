package com.zouzoutingting.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月7日
 */
public class ZipUtil {

	/** 
     * 对文件或文件目录进行压缩 
     * @param srcPath 要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径 
     * @param zipPath 压缩文件保存的路径。注意：zipPath不能是srcPath路径下的子文件夹 
     * @param zipFileName 压缩文件名 
     * @throws Exception 
     */  
	public static void zip(String srcPath, String zipPath, String zipFileName) throws Exception {
		if(StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(zipPath) || StringUtils.isEmpty(zipFileName)) {
			throw new NullPointerException();  
		}
		
		CheckedOutputStream cos = null;
		ZipOutputStream zos = null;
		try {
			
			File srcFile = new File(srcPath);
			// 判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）  
			if(srcFile.isDirectory() && zipPath.indexOf(srcPath) != -1) {
				return ;
			}
			
			File zipDir = new File(zipPath);
			// 判断压缩文件保存的路径是否存在，如果不存在，则创建目录  
			if(!zipDir.exists() || !zipDir.isDirectory()) {
				zipDir.mkdirs();
			}
			
			// 创建压缩文件保存的文件对象  
			String zipFilePath = zipPath + File.separator + zipFileName;
			File zipFile = new File(zipFilePath);
			if(zipFile.exists()) {
				// 检测文件是否允许删除，如果不允许删除，将会抛出SecurityException  
				SecurityManager securityManager = new SecurityManager();
				securityManager.checkDelete(zipFilePath);
				// 删除已存在的目标文件  
				zipFile.delete();
			}
		
		
			cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
			zos = new ZipOutputStream(cos);
			
			// 如果只是压缩一个文件，则需要截取该文件的父目录  
			String srcRootDir = srcPath;
			if(srcFile.isFile()) {
				int index = srcPath.lastIndexOf(File.separator);
				if(index != -1) {
					srcRootDir = srcPath.substring(0, index);
				}
			}
			
			// 调用递归压缩方法进行目录或文件压缩  
			zip(srcRootDir, srcFile, zos);
			zos.flush();
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if(zos != null) {
				zos.close();
			}
			if(cos != null) {
				cos.close();
			}
		}
		
	}
	
	
	/** 
     * 递归压缩文件夹 
     * @param srcRootDir 压缩文件夹根目录的子路径 
     * @param file 当前递归压缩的文件或目录对象 
     * @param zos 压缩文件存储对象 
	 * @throws Exception 
     */  
	private static void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception {
		
		if(file == null) {
			throw new NullPointerException();
		}
		
		BufferedInputStream bis = null;
		InputStream is = null;
		try {
			// 如果是文件，则直接压缩该文件
			if(file.isFile()) {
				int count, bufferLen = 1024;
				byte[]  data = new byte[bufferLen];
				
				// 获取文件相对于压缩文件夹根目录的子路径
				String subPath = file.getAbsolutePath();
				int index = subPath.indexOf(srcRootDir);
				if(index != -1) {
					subPath = subPath.substring(srcRootDir.length() + File.separator.length());
				}
						
				ZipEntry entry = new ZipEntry(subPath);
				zos.putNextEntry(entry);
				
				is = new FileInputStream(file);
				bis = new BufferedInputStream(is);
				while((count = bis.read(data, 0, bufferLen)) != -1) {
					zos.write(data, 0, count);
				}
				
			} else {
				// 如果是目录，则压缩整个目录
				File[] childFileList = file.listFiles();
				for(int n = 0; n < childFileList.length; n++) {
					childFileList[n].getAbsolutePath().indexOf(file.getAbsolutePath());
					zip(srcRootDir, childFileList[n], zos);
				}
			}
		} catch(Exception e) {
			throw e;
		} finally {
			if(is != null) {
				is.close();
			}
			
			if(bis != null) {
				bis.close();
			}
			
			if(zos != null) {
				zos.closeEntry();
			}
			
		}
	}

	/** 
     * 解压缩zip包 
     * @param zipFilePath zip文件的全路径 
     * @param unzipFilePath 解压后的文件保存的路径 
     * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含 
     */  
	@SuppressWarnings("unchecked")
	public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws IOException {
		if(StringUtils.isEmpty(zipFilePath) || StringUtils.isEmpty(unzipFilePath)) {
			throw new NullPointerException();  
		}
		
		File zipFile = new File(zipFilePath);
		// 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径  
		if(includeZipFileName) {
			String fileName = zipFile.getName();
			if(StringUtils.isNotEmpty(fileName)) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			unzipFilePath = unzipFilePath + File.separator + fileName;
		}
		
		// 创建解压缩文件保存的路径 
		File unzipFileDir = new File(unzipFilePath);
		if(!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
			unzipFileDir.mkdirs();
		}
		
		// 开始解压
		ZipEntry entry = null;
		String entryFilePath = null, entryDirPath = null;
		File entryFile = null, entryDir = null;
		int index = 0, count = 0, bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		
		OutputStream os = null;
		BufferedInputStream bis = null;
		InputStream is = null;
		BufferedOutputStream bos = null;
		
		try {
			ZipFile zip = new ZipFile(zipFile);
			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
			// 循环对压缩包里的每一个文件进行解压       
			while(entries.hasMoreElements()) {
				entry = entries.nextElement();
				// 构建压缩包中一个文件解压后保存的文件全路径  
				entryFilePath = unzipFilePath + File.separator + entry.getName();
				// 构建解压后保存的文件夹路径  
				index = entryFilePath.lastIndexOf(File.separator);
				
				if(index != -1) {
					entryDirPath = entryFilePath.substring(0, index);
				} else {
					entryDirPath = "";
				}
				
				entryDir = new File(entryDirPath);
				
				// 如果文件夹路径不存在，则创建文件夹  
				if(!entryDir.exists() || !entryDir.isDirectory()) {
					entryDir.mkdirs();
				}
				
				// 创建解压文件
				entryFile = new File(entryFilePath);
				if(entryFile.exists()) {
					// 检测文件是否允许删除，如果不允许删除，将会抛出SecurityException  
					SecurityManager securityManager = new SecurityManager();
					securityManager.checkDelete(entryFilePath);
					// 删除已存在的目标文件  
					entryFile.delete();
				}
				
				os = new FileOutputStream(entryFile);
				bos = new BufferedOutputStream(os);
				is = zip.getInputStream(entry);
				bis = new BufferedInputStream(is);
				while((count = bis.read(buffer, 0, bufferSize)) != -1) {
					bos.write(buffer, 0, count);
				}
			}
		} catch (ZipException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if(bis != null) {
				bis.close();
			}
			
			if(is != null) {
				is.close();
			}
			
			if(bos != null) {
				bos.close();
			}
			
			if(os != null) {
				os.close();
			}
		}
	}
	
}
