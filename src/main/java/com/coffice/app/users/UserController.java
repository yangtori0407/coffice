package com.coffice.app.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@GetMapping("login")
	public void login() throws Exception {
		
	}
	
	@GetMapping("register")
	public void register(@ModelAttribute UserVO userVO) throws Exception {
		
	}
	
	@PostMapping("register")
	public String register(@Validated(RegisterGroup.class) UserVO userVO, BindingResult bindingResult, @RequestParam("file") MultipartFile file) throws Exception {
		//System.out.println("등록요청");
		//System.out.println("password: " + userVO.getPassword());
	    //System.out.println("passwordCheck: " + userVO.getPasswordCheck());
	    
		if(userService.userErrorCheck(userVO, bindingResult)) {
			//System.out.println("유효성 검사 실패");
			return "user/register";
		}
		
		userService.register(userVO, file);
		//System.out.println("등록완료");
		return "redirect:/";
	}

}
