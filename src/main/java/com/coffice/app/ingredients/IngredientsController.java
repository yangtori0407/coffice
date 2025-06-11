package com.coffice.app.ingredients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coffice.app.page.Pager;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
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
		IngredientsVO ingredientsVO2 = ingredientsService.getDetail(ingredientsVO);
		model.addAttribute("vo", ingredientsVO2);
		
		List<IngredientsVO> list = ingredientsService.getHistory(ingredientsVO);
			

		model.addAttribute("list", list);
		return "ingredients/detail";
	}
	
	@GetMapping("add")
	public String add(Model model) throws Exception {
		 model.addAttribute("ingredientsVO", new IngredientsVO());
		return "ingredients/add";
	}
	
	@PostMapping("add")
	public String add(@Validated @ModelAttribute IngredientsVO ingredientsVO, BindingResult bindingResult) throws Exception {
		  log.info("ingredientsName = {}", ingredientsVO.getIngredientsName());
		    log.info("bindingResult.hasErrors() = {}", bindingResult.hasErrors());
		
		if(bindingResult.hasErrors()) {
			log.info("Validation errors found.");
		    return "ingredients/add";
		}
		
		// 이름 중복 검사
		if (ingredientsService.nameErrorCheck(ingredientsVO, bindingResult)) {
			return "ingredients/add";
		}
	    
		ingredientsService.add(ingredientsVO);
		return "redirect:./list";
	}
	
	@PostMapping("addHistory")
	public String addHistory(History history) throws Exception {
		log.info("I:{}",history);
		History history2 = new History();
		history2.setReceive(history.isReceive());
		history2.setNumber(history.getNumber());
		history2.setUserId("A12");
		history2.setIngredientsID(history.getIngredientsID());
		
		ingredientsService.addHistory(history2);
		
		return "redirect:./list";
	}
}
