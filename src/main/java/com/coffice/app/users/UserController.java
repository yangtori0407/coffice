package com.coffice.app.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coffice.app.mail.MailService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
    private UserService userService;
	@Autowired
	private MailService mailService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("login")
	public void login() throws Exception {
		
	}
	
	@GetMapping("register")
	public void register(@ModelAttribute UserVO userVO) throws Exception {
		
	}
	
	@PostMapping("register")
	public String register(@Validated(RegisterGroup.class) UserVO userVO, BindingResult bindingResult, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
		//System.out.println("등록요청");
		//System.out.println("password: " + userVO.getPassword());
	    //System.out.println("passwordCheck: " + userVO.getPasswordCheck());
	    
		if(userService.userErrorCheck(userVO, bindingResult)) {
			//System.out.println("유효성 검사 실패");
			return "user/register";
		}
		
		userService.register(userVO, file);
		//System.out.println("등록완료");
		redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다!");
		return "redirect:/";
	}
	
	@GetMapping("afterLogout")
	public void afterLogout() throws Exception {
		
	}
	
	@GetMapping("forgotPw")
	public void forgotPw() throws Exception {
		
	}
	
	@PostMapping("forgotPw")
	public String forgotPw(@RequestParam("email") String email, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		try {
			//이메일 확인
			UserVO userVO = userDAO.findByEmail(email);
			if (userVO == null) {
				redirectAttributes.addFlashAttribute("error", "존재하지 않는 이메일입니다.");
				return "redirect:/user/forgotPw";
			}

			mailService.sendAuthCode(email, session);
			
			session.setAttribute("resetEmail", email);
			
			redirectAttributes.addFlashAttribute("msg", "인증 메일이 전송되었습니다.");
			return "redirect:/user/verifyCode";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "메일 전송 실패: "+ e.getMessage());
			return "redirect:/user/forgotPw";
		}
	}
	
	@GetMapping("verifyCode")
	public void verifyCode () throws Exception {
		
	}
	
	@PostMapping("verifyCode")
	public String verifyCode(@RequestParam("code") String inputCode, HttpSession session, RedirectAttributes redirectAttributes) throws Exception{
		String sessionCode = (String)session.getAttribute("authCode");
		String email = (String)session.getAttribute("resetEmail");
		
		if(inputCode != null && inputCode.equals(sessionCode)) {
			session.setAttribute("verifiedEmail", email);
			
			UserVO userVO = new UserVO();
			userVO.setEmail(email);
			userVO = userService.findByEmail(email);
			
			if(userVO != null) {
				String userId = userVO.getUserId();
				redirectAttributes.addFlashAttribute("msg", "인증 성공하였습니다.");
				return "redirect:/user/resetPw?userId=" + userId;
			} else {
				redirectAttributes.addFlashAttribute("check", "해당 이메일로 등록된 사용자가 없습니다.");
				return "redirect:/user/verifyCode?error=nouser";
			}
			
		}else {
			redirectAttributes.addFlashAttribute("no", "인증 코드가 일치하지 않습니다.");
			return "redirect:/user/verifyCode?error=true";
		}
	}
	
	@GetMapping("resetPw")
	public String resetPw(@RequestParam String userId, Model model) throws Exception {
		model.addAttribute("userId", userId);
		return "user/resetPw";
	}
	
	@PostMapping("resetPw")
	public String resetPw(@RequestParam String userId,
						  @RequestParam String password,
						  @RequestParam String passwordCheck,
						  RedirectAttributes redirectAttributes) throws Exception {
		
		if(!password.equals(passwordCheck)) {
			redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다");
			return "redirect:/user/resetPw?userId="+userId;
		}
		
		String encodedPassword = passwordEncoder.encode(password);
		
		UserVO userVO = new UserVO();
		userVO.setUserId(userId);
		userVO.setPassword(encodedPassword);
		
		int result = userService.updatePassword(userVO);
		
		UserVO check = userService.detail(userVO); // detail 메서드 맞는지 확인
	    System.out.println("🔍 저장 직후 비밀번호: " + check.getPassword());
	    
		if(result>0) {
			redirectAttributes.addFlashAttribute("msg", "비밀번호가 번경되었습니다");
			return "redirect:/user/login";
		}else {
			redirectAttributes.addFlashAttribute("msg", "비밀번호 변경 실패");
			return "redirect:/user/resetPw?userId=" + userId;
		}
	}

}
