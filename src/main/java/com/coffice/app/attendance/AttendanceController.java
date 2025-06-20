package com.coffice.app.attendance;

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
		String userId = ((UserVO)session.getAttribute("user")).getUserId();
		return attendanceService.checkIn(userId);
	}
	
	@PostMapping("/checkOut")
	@ResponseBody
	public boolean checkOut(HttpSession session) throws Exception{
		String userId = ((UserVO)session.getAttribute("user")).getUserId();
		return attendanceService.checkOut(userId);
	}
	
	@GetMapping("/todayStatus")
	@ResponseBody
	public AttendanceVO todayStatus(HttpSession session) throws Exception {
		
		UserVO userVO = (UserVO)session.getAttribute("user");
		if(userVO == null) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}
		String userId = userVO.getUserId();
		return attendanceService.todayStatus(userId);
	}


}
