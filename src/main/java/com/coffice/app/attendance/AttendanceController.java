package com.coffice.app.attendance;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.users.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
	@PostMapping("/checkIn")
	@ResponseBody
	public boolean checkIn(HttpSession session) throws Exception{
		UserVO userVO = (UserVO) session.getAttribute("user");
		if (userVO == null) return false;

	    return attendanceService.checkIn(userVO.getUserId());
	}
	
	@PostMapping("/checkOut")
	@ResponseBody
	public boolean checkOut(HttpSession session) throws Exception{
		UserVO user = (UserVO) session.getAttribute("user");
		if (user == null) return false;

	    return attendanceService.checkOut(user.getUserId());
	}
	
	@GetMapping("/todayStatus")
	@ResponseBody
	public AttendanceVO todayStatus(HttpSession session) throws Exception {
		
		UserVO userVO = (UserVO)session.getAttribute("user");
		if(userVO == null) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}
		String userId = userVO.getUserId();
		LocalDate today = LocalDate.now();
		
		return attendanceService.todayStatus(userId, today);
	}


}
