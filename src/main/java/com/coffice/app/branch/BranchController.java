package com.coffice.app.branch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/branch/*")
public class BranchController {
	
	@Value("${kakao.map.appkey}")
	private String appkey;
	
	@ModelAttribute("appkey")
	public String getAppkey() {
		
		return this.appkey;
	}

	@GetMapping("map")
	public String map() throws Exception {
		return "branch/map";
	}
}
