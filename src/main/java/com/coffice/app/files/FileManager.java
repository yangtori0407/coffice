package com.coffice.app.files;

import java.io.File;

import java.util.List;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager {
	

	
	public String fileSave(String path, MultipartFile multipartFile) throws Exception{

		File file = new File(path);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		

		String fileName = UUID.randomUUID().toString();
		
		fileName = fileName.concat("_").concat(multipartFile.getOriginalFilename());
		
		file = new File(file, fileName);

		multipartFile.transferTo(file);
		
		return fileName;
	}

	
	
	// 파일 삭제
	public void fileDelete(String path, String fileName) throws Exception {
		
		File file = new File(path, fileName);
		
		if(file.exists()) {
			boolean check = file.delete();
		}
		
	}
	
	
//	public void quillFileDelete(String path, List<String> files) throws Exception{
//		for(String f : files) {
//			File file = new File(path, f);
//			
//		}
//	}

}
