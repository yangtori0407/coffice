package com.coffice.app.files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer{
	
	@Value("${app.files.base}")
	private String path;
	
	@Value("${app.files.url}")
	private String url;
	
	@Value("${app.profiles.url}")
	private String profileUrl;
	
	@Value("${app.profiles.base}")
	private String profilePath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//url 경로로 온 요청을 file:\\ + path 경로로 연결해준다.
		//file 뜻 => 로컬디스크에 있는 폴더야~!
		registry.addResourceHandler(url).addResourceLocations("file:\\" + path);
		
		registry
			.addResourceHandler(profileUrl)
			.addResourceLocations("file:\\"+ profilePath)
			;
	}
	
}
