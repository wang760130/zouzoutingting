package com.zouzoutingting.controllers;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.swetake.util.Qrcode;
import com.zouzoutingting.utils.MD5;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年7月9日
 */

@Controller
public class QrCodeController extends BaseController {
	
	private static final String QRCODE_URL = "http://www.imonl.com/mobile";
	private static final String SUFFIX = "jpg";
	
	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	public ModelAndView qrcode(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
	    return new ModelAndView("qrcode");
	}
	
	@RequestMapping(value = "/generateqrcode", method = RequestMethod.GET)
	public void generateqrcode(HttpServletRequest request, HttpServletResponse response) {
		
		String fileName = MD5.Md5Encryt(QRCODE_URL);
		File file = new File(fileName + "." + SUFFIX);
		
		try {
			BufferedImage bufferedImage = this.encoderQRCode(QRCODE_URL);
			ImageIO.write(bufferedImage, SUFFIX, file);
			this.imgaeResult(file, response);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		} finally {
			if(file != null && file.exists()) {
				file.delete();
			}
		}
	}
	
	@RequestMapping(value = "/downloadqrcode", method = RequestMethod.GET)
	public void downloadqrcode(HttpServletRequest request, HttpServletResponse response) {
		
		String fileName = MD5.Md5Encryt(QRCODE_URL);
		File file = new File(fileName + "." + SUFFIX);
		
		BufferedImage bufferedImage = this.encoderQRCode(QRCODE_URL);
		try {
			ImageIO.write(bufferedImage, SUFFIX, file);
			this.downloadResult(file, response);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		} finally {
			if(file != null && file.exists()) {
				file.delete();
			}
		}
		
	}
	
	private BufferedImage encoderQRCode(String content) {
		BufferedImage bufferedImage = null;
		try {
			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect('M');
			qrcode.setQrcodeEncodeMode('B');
			qrcode.setQrcodeVersion(7);
			
			byte[] contentBytes = content.getBytes("UTF-8");
			
			bufferedImage = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = bufferedImage.createGraphics();
			
			graphics2D.setBackground(Color.WHITE);
			graphics2D.clearRect(0, 0, 160, 160);  
			graphics2D.setColor(Color.BLACK);
			
			int pixoff = 2;
			if(contentBytes.length > 0 && contentBytes.length < 124) {
				boolean[][] codeOut = qrcode.calQrcode(contentBytes);
				for(int i = 0; i < codeOut.length; i++) {
					for(int j = 0; j < codeOut.length; j++) {
						if(codeOut[j][i]) {
							graphics2D.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} 
			
			graphics2D.dispose();
			bufferedImage.flush();  
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage(), e);
		} 
		
		return bufferedImage;
	}

}
