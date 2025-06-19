package com.coffice.app.ingredients;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.page.Pager;
import com.coffice.app.sales.MenuVO;
import com.coffice.app.sales.SalesService;
import com.coffice.app.sales.SalesVO;
import com.coffice.app.users.UserVO;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/ingredients/*")
public class IngredientsController {
	
	@Autowired
	private IngredientsService ingredientsService;
	@Autowired
	private SalesService salesService;

	@GetMapping("list")
	public String getList(Model model, Pager pager) throws Exception {
		
		List<IngredientsVO> list = ingredientsService.getList(pager);
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		
		List<IngredientsVO> totalList = ingredientsService.totlaList();
		model.addAttribute("totalList", totalList);
		
		model.addAttribute("ingredientsVO", new IngredientsVO());
		return "ingredients/list";
	}
	
	@GetMapping("detail")
	public String getDetail(IngredientsVO ingredientsVO, Model model, Pager pager) throws Exception {
		IngredientsVO ingredientsVO2 = ingredientsService.getDetail(ingredientsVO);
		model.addAttribute("vo", ingredientsVO2);
		
		List<IngredientsVO> list = ingredientsService.getHistory(ingredientsVO, pager);
		
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		return "ingredients/detail";
	}
	
	
	@PostMapping("add")
	@ResponseBody
	public HashMap<String, Object> add(@Validated @ModelAttribute IngredientsVO ingredientsVO, BindingResult bindingResult) throws Exception {
		  log.info("ingredientsName = {}", ingredientsVO.getIngredientsName());
		  log.info("bindingResult.hasErrors() = {}", bindingResult.hasErrors());
		    
		   HashMap<String, Object> map = new HashMap<>();
		   
		if(bindingResult.hasErrors()) {
			log.info("Validation errors found.");
			map.put("status", "fail");
			map.put("message", "이름이 필요합니다.");
		    return map;
		}
		
		// 이름 중복 검사
		if (ingredientsService.nameErrorCheck(ingredientsVO, bindingResult)) {
			map.put("status", "fail");
			map.put("message", "이미 존재하는 상품입니다.");
	        return map;
		}
	    
		ingredientsService.add(ingredientsVO);
		map.put("status", "success");
		map.put("message", "추가되었습니다.");
	    return map;
	}
	
	@PostMapping("addHistory")
	@ResponseBody
	@Transactional
	public int addHistory(@AuthenticationPrincipal UserVO userVO, History history) throws Exception {
		History history2 = new History();
		log.info("h1:{}",history);
		log.info("h a:{}",history2);
		
			history2.setHistoryId(history.getHistoryId());
			history2.setReceive(history.isReceive());
			history2.setNumber(history.getNumber());
			history2.setUserId(userVO.getUserId());
			history2.setIngredientsID(history.getIngredientsID());			
		

		ingredientsService.addHistory(history2);
		int result2 = ingredientsService.plusStock(history2);
		
		if(result2==0) {
			throw new RuntimeException("출고불가");
		}
		
		return result2;
	}
	
	@GetMapping("menuList")
	@ResponseBody
	public List<MenuVO> selectMenu() throws Exception {
		List<MenuVO> menuList = salesService.menuList();
		return menuList;
	}
	
	@GetMapping("ingredientsList")
	@ResponseBody
	public List<IngredientsVO> selectIngredients() throws Exception {
		List<IngredientsVO> ingredientsList = ingredientsService.totlaList();
		return ingredientsList;
	}
	
	@PostMapping("profit")
	@ResponseBody
	public void profit(SalesVO salesVO) throws Exception {
		log.info("p:{}",salesVO);
	}
	
	@PostMapping("expenditure")
	@ResponseBody
	public void expenditure(SalesVO salesVO) throws Exception {
		log.info("e:{}",salesVO);
	}
}
