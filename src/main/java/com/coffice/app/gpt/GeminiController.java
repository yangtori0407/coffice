package com.coffice.app.gpt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coffice.app.sales.SalesService;

@RestController
@RequestMapping("/gpt/*")
public class GeminiController {

	@Autowired
	private GeminiService productService;
	@Autowired
	private SalesService salesService;
	
	@PostMapping("description")
	public Object description(@RequestBody PromptVO productNameVO) throws Exception {
		Object prompt = "";
		String str = productNameVO.getPrompt().trim().toLowerCase();
		
		Pattern p = Pattern.compile("coffice\\s*(메뉴||매뉴||menu)");
		Matcher m = p.matcher(str);
		
		Pattern p2 = Pattern.compile("매출\\s*");
		
		if(m.find()) {
			prompt = salesService.menuList();
		}else {
			prompt = productService.getDescription(productNameVO.getPrompt());
		}
		
		return prompt;
	}
}
