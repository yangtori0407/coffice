package com.coffice.app.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FileDownView extends AbstractView{
	
	@Value("${app.files.base}")
	private String path;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FileVO fileVO = (FileVO)model.get("fileVO");
		String kind = (String)model.get("kind");
		
		File file = new File(path.concat(kind), fileVO.getSaveName());
		
		response.setContentLengthLong(file.length());
		
		String name = URLEncoder.encode(fileVO.getOriginName(), "UTF-8");
		
		response.setHeader("Content-Disposition", "attachment;fileName=\"".concat(name).concat("\""));
		
		FileInputStream fr = new FileInputStream(file);
		
		OutputStream os = response.getOutputStream();
		
		FileCopyUtils.copy(fr, os);
		
		os.close();
		fr.close();
		
	}
}
