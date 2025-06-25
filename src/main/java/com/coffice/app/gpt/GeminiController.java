package com.coffice.app.gpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpt/*")
public class GeminiController {

	@Autowired
	private GeminiService productService;
	
	@PostMapping("description")
	public String description(@RequestBody PromptVO productNameVO) throws Exception {
		return productService.getDescription(productNameVO.getPrompt());
	}
}
