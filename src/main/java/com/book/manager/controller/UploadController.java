package com.book.manager.controller;

import com.book.manager.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公用的上传类
 * @author Administrator
 *
 */
@RequestMapping("/upload")
@Controller
public class UploadController {

	@Value("${yuanmu.sufix}")
	private String uploadPhotoSufix;

	@Value("${yuanmu.maxsize}")
	private long uploadPhotoMaxSize;

	@Value("${yuanmu.uploadPath}")
	private String uploadPhotoPath;//文件保存位置

	private Logger log = LoggerFactory.getLogger(UploadController.class);

	/**
	 * 图片统一上传类
	 * @param photo
	 * @return
	 */
	@RequestMapping(value="/upload_photo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadPhoto(@RequestParam(name="photo",required=true)MultipartFile photo){
		Map<String, Object> ret = new HashMap<>();
		ret.put("type","error");
		//判断文件类型是否是图片
		String originalFilename = photo.getOriginalFilename();
		//获取文件后缀
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."),originalFilename.length());
		if(!uploadPhotoSufix.contains(suffix.toLowerCase())){
			ret.put("message","请上传正确的图片形式");
			return ret;
		}
		if(photo.getSize()/1024 > uploadPhotoMaxSize){
			ret.put("message","图片大小不能超过" + (uploadPhotoMaxSize/1024) + "M");
			return ret;
		}
		//准备保存文件
		File filePath = new File(uploadPhotoPath);
		if(!filePath.exists()){
			//若不存在文件夹，则创建一个文件夹
			filePath.mkdir();
		}
		filePath = new File(uploadPhotoPath + "/" + StringUtil.getFormatterDate(new Date(), "yyyyMMdd"));
		//判断当天日期的文件夹是否存在，若不存在，则创建
		if(!filePath.exists()){
			//若不存在文件夹，则创建一个文件夹
			filePath.mkdir();
		}
		String filename = StringUtil.getFormatterDate(new Date(), "yyyyMMdd") + "/" + System.currentTimeMillis() + suffix;
		try {
			photo.transferTo(new File(uploadPhotoPath+"/"+filename));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("图片上传成功，保存位置：" + uploadPhotoPath + filename);
		ret.put("type","success");
		ret.put("data",filename);
		return ret;
	}


}
