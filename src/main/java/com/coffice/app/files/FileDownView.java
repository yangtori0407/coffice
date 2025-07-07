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

@Component("fileDownView")
public class FileDownView extends AbstractView {
	
	@Value("${app.files.base}")
	private String path;
	
	
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,	
			HttpServletResponse response) throws Exception {
		
		FileVO fileVO = (FileVO)model.get("fileVO");
		String kind = (String)model.get("kind");
		
		//System.out.println(path.concat(kind) + " : : " + fileVO.getSaveName());
		
		File file = new File(path.concat(kind), fileVO.getSaveName());
		
		// 파일 존재 여부 확인
		if (!file.exists() || !file.isFile()) {
			// 404 상태 설정 후 /error로 리다이렉트
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.sendRedirect("/error");
			return;
		}
		
		
		// try with resources : try 블록 안에서 열린 모든 AutoCloseable 객체는 자동으로 close()가 호출된다. (Java7 이상)
		try (
			FileInputStream fr = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
				
		) {
			// 응답 설정
			response.setContentLengthLong(file.length());
			
			String name = URLEncoder.encode(fileVO.getOriginName(), "UTF-8");		
			response.setHeader("Content-Disposition", "attachment;fileName=\"".concat(name).concat("\""));
			
			FileCopyUtils.copy(fr, os);
			
		} catch (Exception e) {
		
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendRedirect("/error");
			
		}
		
		
	}
	
}
