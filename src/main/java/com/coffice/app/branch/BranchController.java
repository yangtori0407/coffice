package com.coffice.app.branch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.page.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/branch/*")
public class BranchController {
	
	@Value("${kakao.map.appkey}")
	private String appkey;
	@Autowired
	private BranchService branchService;
	
	@ModelAttribute("appkey")
	public String getAppkey() {
		
		return this.appkey;
	}

	@GetMapping("map")
	public String map(Model model, Pager pager) throws Exception {
		List<BranchVO> list = branchService.getList(pager);
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		
		log.info("list:{}",list);
		log.info("kind:{}",pager.getKind());
		log.info("search:{}",pager.getSearch());
		
		List<BranchVO> notAddBranchList = branchService.notAddBranchList();
		model.addAttribute("notAddBranchList", notAddBranchList);
		
		List<BranchMasterVO> notAddBranchMasterList = branchService.notAddBranchMasterList();
		model.addAttribute("notAddBranchMasterList", notAddBranchMasterList);
		
		return "branch/map";
	}
	
	@GetMapping("add")
	public String add() throws Exception {
		return "branch/add";
	}
	@PostMapping("add")
	public String add(BranchVO branchVO) throws Exception {
		int result = branchService.add(branchVO);
		
		
		if(result > 0) {
			return "redirect:/";
		}
		return "branch/add";
	}
	@PostMapping("updateUser")
	public String updateUser(BranchVO branchVO) throws Exception {
		log.info("b:{}",branchVO);
		BranchVO branchVO2 = new BranchVO();
		branchVO2.setBranchId(branchVO.getBranchId());
		branchVO2.setUserId(branchVO.getUserId());
		
		branchService.branchUpdate(branchVO2);
		
		return "redirect:./map";
	}
	
	@GetMapping("detail")
	@ResponseBody
	public BranchVO detail(BranchVO branchVO) throws Exception {
		return branchService.getDetail(branchVO);
		
	}
}
