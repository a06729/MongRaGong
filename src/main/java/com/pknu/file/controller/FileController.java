package com.pknu.file.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FileController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public Map fileUpload(HttpServletRequest req,HttpServletResponse resp) {
		String path="C:\\Users\\Administrator\\Desktop\\test";
		Map returnObject=new HashMap();
		
		try {
			MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) req;
			Iterator iter=mhsr.getFileNames();
			
			MultipartFile mfile=null;
			String fileName="";
			List resultList=new ArrayList();
			
			File dir=new File(path);
			if(!dir.isDirectory()) {
				dir.mkdirs();
			}
			
			while(iter.hasNext()) {
				fileName=(String) iter.next();
				mfile=mhsr.getFile(fileName);
				String origName;
				
				origName=new String(mfile.getOriginalFilename().getBytes("8859_1"),"UTF-8");
				
				if("".equals(origName)) {
					continue;
				}
				
				String ext=origName.substring(origName.lastIndexOf("."));
				String saveFileName=getUuid()+ext;
				
				File serverFile=new File(path+File.separator+saveFileName);
				
				mfile.transferTo(serverFile);
				
				Map file=new HashMap();
				file.put("origName",origName);
				file.put("sfile", serverFile);
				resultList.add(file);
			}
			returnObject.put("files",resultList);
			returnObject.put("params",mhsr.getParameterMap());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	
}
