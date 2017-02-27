package cn.mylogger.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mylogger.utils.CommonUtils;
import cn.mylogger.utils.PropertiesUtil;
import cn.mylogger.utils.ResultMap;

@Controller
public class FileController {

	@ResponseBody
	@RequestMapping(value = "/upload.do")
	public ResultMap upload(@RequestParam(value = "files", required = false) MultipartFile[] files,
			HttpServletRequest request) {
		ResultMap rm = new ResultMap();
		try {
			System.out.println("开始");
			Properties upoladPro = PropertiesUtil.getProperties("upload.properties");
			String path = upoladPro.getProperty("revert_path");
			List<String> urls = new ArrayList<String>();
			for (MultipartFile file : files) {
				String fileName = file.getOriginalFilename();
				// String fileName = new Date().getTime()+".jpg";
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 保存
				file.transferTo(targetFile);
				urls.add(CommonUtils.getShowPICUrl(request, targetFile.getAbsolutePath()));
			}
			rm.put("urls", urls);
		} catch (Exception e) {
			e.printStackTrace();
			rm.fail("上传文件失败");
		}
		return rm;
	}
}
