package com.coffice.app.ingredients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coffice.app.page.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/ingredients/*")
public class IngredientsController {
	
	@Autowired
	private IngredientsService ingredientsService;

	@GetMapping("list")
	public String getList(Model model, Pager pager) throws Exception {
		
		List<IngredientsVO> list = ingredientsService.getList(pager);
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		return "ingredients/list";
	}
	
	@GetMapping("detail")
	public String getDetail(IngredientsVO ingredientsVO, Model model) throws Exception {
		ingredientsVO = ingredientsService.getDetail(ingredientsVO);
		model.addAttribute("vo", ingredientsVO);
		return "ingredients/detail";
	}
}
