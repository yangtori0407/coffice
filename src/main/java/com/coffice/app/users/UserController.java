package com.coffice.app.users;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coffice.app.gpt.GeminiService;
import com.coffice.app.attendance.AttendanceService;

import com.coffice.app.attendance.AttendanceStatusCountVO;

import com.coffice.app.events.vacation.AnnualLeaveService;
import com.coffice.app.events.vacation.VacationService;

import com.coffice.app.mail.MailService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/*")
public class UserController {

    private final WebSecurityCustomizer customizer;
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
    private UserService userService;

	@Autowired
	private MailService mailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private GeminiService geminiService;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private AnnualLeaveService annualLeaveService;
	
	@Autowired
	private VacationService vacationService;


    UserController(WebSecurityCustomizer customizer) {
        this.customizer = customizer;
    }

	
	@GetMapping("login")
	public String login() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
	        return "redirect:/";
	    }
	    return "user/login";
		
	}
	
	@GetMapping("register")
	public void register(@ModelAttribute UserVO userVO, Model model) throws Exception {
		model.addAttribute("user", "register");
		model.addAttribute("kind", "사원 등록");
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
		return "redirect:/employee/list";
	}
	
	@GetMapping("afterLogout")
	public void afterLogout() throws Exception {
		
	}
	

	@GetMapping("forgotPw")
	public void forgotPw() throws Exception {
		
	}
	
	@PostMapping("forgotPw")
	public String forgotPw(@RequestParam("userId") String userId,
							@RequestParam("email") String email, 
							HttpSession session, 
							RedirectAttributes redirectAttributes) throws Exception {
		try {
			//사원번호, 이메일 확인
			UserVO userVO = new UserVO();
			userVO.setUserId(userId);
			userVO.setEmail(email);
			
			UserVO checkUser = userDAO.detail(userVO);
			if (checkUser == null || !checkUser.getUserId().equals(userId)) {
				redirectAttributes.addFlashAttribute("error", "존재하지 않는 사원번호입니다.");
				return "redirect:/user/forgotPw";
			}
			
			if (!checkUser.getEmail().equals(email)) {
				redirectAttributes.addFlashAttribute("error", "이메일이 일치하지 않습니다.");
				return "redirect:/user/forgotPw";
			}

			mailService.sendAuthCode(email, session);
			
			session.setAttribute("resetEmail", email);
			session.setAttribute("resetUserId", userId);
			
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
		String userId = (String) session.getAttribute("resetUserId");
		
		System.out.println("[DEBUG] 세션 이메일: " + email);
		System.out.println("[DEBUG] 세션 아이디: " + userId);

		
		if(inputCode != null && inputCode.equals(sessionCode)) {
			session.setAttribute("verifiedEmail", email);
			
			UserVO userVO = userService.findByEmail(email, userId);
			
			if(userVO != null) {
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
		
		try {
		
		int result = userService.updatePassword(userId, password);
	    
		if(result>0) {
			redirectAttributes.addFlashAttribute("reset", "비밀번호가 번경되었습니다");
			return "redirect:/user/login";
		}else {
			redirectAttributes.addFlashAttribute("fail", "비밀번호 변경 실패");
			return "redirect:/user/resetPw?userId=" + userId;
		}
		
	} catch(IllegalArgumentException e) {
        // 비밀번호 유효성 검사 실패
        redirectAttributes.addFlashAttribute("valid", e.getMessage());
        return "redirect:/user/resetPw?userId=" + userId;
    }
	}
	
	@GetMapping("mypage")
	public String mypage(@AuthenticationPrincipal UserVO userVO, Model model) throws Exception{
		model.addAttribute("quote", geminiService.getQuote(userVO.getName()));

		String userId = userVO.getUserId();
		
		Map<String, Long> timeMap = attendanceService.getWeeklyWorkStatus(userId);


		Map<String, Double> annualLeaves = annualLeaveService.getAnnualLeaves(userId);
		
		model.addAttribute("annualLeaves", annualLeaves);

		model.addAttribute("timeMap", timeMap);
		
		int year = LocalDate.now().getYear();
	    int month = LocalDate.now().getMonthValue();

	    List<AttendanceStatusCountVO> statusList = attendanceService.getMonthlyStatusCount(userId, year, month);

	    int normal = 0, earlyLeave = 0, absent = 0;
	    for (AttendanceStatusCountVO vo : statusList) {
	        switch (vo.getStatus()) {
	            case "정상근무" -> normal = vo.getCount();
	            case "조퇴" -> earlyLeave = vo.getCount();
	            case "결근" -> absent = vo.getCount();
	        }
	    }

	    model.addAttribute("normalCount", normal);
	    model.addAttribute("earlyLeaveCount", earlyLeave);
	    model.addAttribute("absentCount", absent);
	    
		model.addAttribute("kind", "마이페이지");
		
		return "user/mypage";
	}
	
	@PostMapping("mypage/checkPassword")
	@ResponseBody
	public Map<String, Boolean> checkPassword(@RequestBody Map<String, String> payload, HttpSession session) throws Exception {
		String inputPw = payload.get("password");
		UserVO loginUser = (UserVO) session.getAttribute("user");
		
		boolean result = userService.checkPassword(loginUser.getUserId(), inputPw);
		return Collections.singletonMap("result", result);
	}
	
	@GetMapping("update")
	public String update(HttpSession session, Model model) throws Exception {
		UserVO userVO = (UserVO)session.getAttribute("user");
		if(userVO != null) {
			model.addAttribute("userVO", userVO);
		}
		model.addAttribute("kind", "내 정보 수정");
		
		return "user/update";
	}
	
	@PostMapping("update")
	public String update(@Valid @ModelAttribute("userVO") UserVO userVO, BindingResult bindingResult,
						@RequestParam("file") MultipartFile file, 
						HttpSession session, RedirectAttributes redirectAttributes, Model model) throws Exception {
		
	    if (userVO.getPassword() != null && !userVO.getPassword().isBlank() &&
	        !userVO.getPassword().equals(userVO.getPasswordCheck())) {
	    	bindingResult.rejectValue("passwordCheck", "error.passwordCheck", "비밀번호 확인이 일치하지 않습니다.");
	    	model.addAttribute("kind", "내 정보 수정");
	        return "user/update";
	    }
	    
	    if (bindingResult.hasErrors()) {
	    	model.addAttribute("kind", "내 정보 수정");
	        return "user/update";
	    }
	    
	    userService.update(userVO, file);

	    UserVO updateUser = userService.findById(userVO.getUserId());
	    session.setAttribute("user", updateUser);

	    redirectAttributes.addFlashAttribute("success", "내 정보가 성공적으로 수정되었습니다!");
	    return "user/mypage";
	}
	
	
	
	// 조직도
	@GetMapping("organizationChart")
	@ResponseBody
	public List<DepartmentVO> getDeps() throws Exception {
		return userService.getDeps();
	}
	
	@GetMapping("getUsers")
	@ResponseBody
	public List<UserVO> getUsers(UserVO userVO) throws Exception {
		return userService.getUsers(userVO);
	}
	
	@GetMapping("user/vacationHistory")
	public String vacationHistory(Model model, Authentication authentication) throws Exception {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		model.addAttribute("kind", "마이 페이지 > 휴가 내역");
		model.addAttribute("requestList", vacationService.getApplyList(userVO));
		model.addAttribute("approvalList", vacationService.getAcceptList(userVO));
		return "user/vacationHistory";
	}
	
	@GetMapping("user/annualLeaveHistory")
	public String annualLeaveHistory(Model model, Authentication authentication) throws Exception {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		model.addAttribute("kind", "마이 페이지 > 연차 내역");
		model.addAttribute("allList", annualLeaveService.getList(userVO));
		return "user/annualLeaveHistory";
	}

}
