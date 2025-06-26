package com.coffice.app.gpt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coffice.app.branch.BranchService;
import com.coffice.app.ingredients.IngredientsService;
import com.coffice.app.sales.SalesService;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gpt/*")
@Slf4j
public class GeminiController {

	@Autowired
	private GeminiService geminiService;
	@Autowired
	private SalesService salesService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private IngredientsService ingredientsService;
	
	@PostMapping("description")
	public Object description(@RequestBody PromptVO productNameVO) throws Exception {
		Object prompt = "";
		String str = productNameVO.getPrompt().trim().toLowerCase();
		
		Pattern p = Pattern.compile(".*(메뉴|매뉴|menu).*");
		Matcher m = p.matcher(str);
		
		Pattern p2 = Pattern.compile(".*(총매출|총메출).*");
		Matcher m2 = p2.matcher(str);
		
		Pattern p3 = Pattern.compile(".*(지점별매출|지점간매출|지점마다매출|지점들의매출).*");
		Matcher m3 = p3.matcher(str);
		
		Pattern p4 = Pattern.compile(".*(식자재|재료).*");
		Matcher m4 = p4.matcher(str);
		
		if(m.find()) {
			prompt = salesService.menuList();
		}else if(m2.find()) {
			prompt = branchService.totalSales();
		}else if(m3.find()) {
			prompt = branchService.getTotalBranchSaleList();
		}else if(m4.find()) {
			prompt = ingredientsService.totlaList();
		}else {
			prompt = geminiService.getDescription(productNameVO.getPrompt());
		}
		log.info("p:{}",prompt);
		return prompt;
	}
	
	@GetMapping("quote")
	public String quote(@AuthenticationPrincipal UserVO userVO) throws Exception {
		return geminiService.getQuote(userVO.getName());
	}
}
