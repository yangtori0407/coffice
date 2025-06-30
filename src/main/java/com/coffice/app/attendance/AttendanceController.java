package com.coffice.app.attendance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.employee.EmployeeService;
import com.coffice.app.employee.EmployeeVO;
import com.coffice.app.page.Pager;
import com.coffice.app.users.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private EmployeeService employeeService;
	
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
	
	@GetMapping("/weeklyStatus")
	public String showWorkStatus(Model model, HttpSession session) throws Exception{
		String userId = ((UserVO) session.getAttribute("user")).getUserId();
		Map<String, Long> status = attendanceService.getWeeklyWorkStatus(userId);
	    model.addAttribute("timeMap", status);
	    
		return "/user/mypage";
	}
	
	@PostMapping("/attendance/autoAbsence")
	public String processAutoAbsences() throws Exception {
	
		attendanceService.insertAndUpdateAbsences();
		
		return "redirect:/attendance/list";
	}
	
	@GetMapping("/statusUpdate")
	public String statusUpdate(@RequestParam String userId, Pager pager, Model model) throws Exception {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		EmployeeVO employeeVO = employeeService.getEmployeeById(userId);
		List<AttendanceVO> attendanceList = attendanceService.getAttendanceListByUser(userId);

		// 시간만 띄우기 위함 
		for (AttendanceVO a : attendanceList) {
		    if (a.getStartTime() != null) {
		        a.setStartTimeStr(a.getStartTime().format(timeFormatter));
		    }
		    if (a.getEndTime() != null) {
		        a.setEndTimeStr(a.getEndTime().format(timeFormatter));
		    }
		}

		
		model.addAttribute("employeeVO", employeeVO);
		model.addAttribute("attendanceList", attendanceList);
		model.addAttribute("pager", pager);
		model.addAttribute("kind", "사원 근태 수정");
		
		return "attendance/statusUpdate";
	}
	
	@PostMapping("/updateStatus")
	public String updateAttendance(@ModelAttribute AttendanceVO attendanceVO) throws Exception {
		//System.out.println("=== [폼 입력값] ===");
	    //System.out.println("userId = " + attendanceVO.getUserId());
	    //System.out.println("attendanceDate = " + attendanceVO.getAttendanceDate());
	    //System.out.println("startTimeInput = " + attendanceVO.getStartTimeInput());
	    //System.out.println("endTimeInput = " + attendanceVO.getEndTimeInput());
	    //System.out.println("status = " + attendanceVO.getStatus());
	    //System.out.println("reason = " + attendanceVO.getReason());
	    
	    // LocalTime + LocalDate → LocalDateTime으로 조립
	    if (attendanceVO.getStartTimeInput() != null && attendanceVO.getAttendanceDate() != null) {
	        attendanceVO.setStartTime(attendanceVO.getAttendanceDate().atTime(attendanceVO.getStartTimeInput()));
	    }

	    if (attendanceVO.getEndTimeInput() != null && attendanceVO.getAttendanceDate() != null) {
	        attendanceVO.setEndTime(attendanceVO.getAttendanceDate().atTime(attendanceVO.getEndTimeInput()));
	    }
	    
	    //System.out.println("startTime = " + attendanceVO.getStartTime());
	    //System.out.println("endTime = " + attendanceVO.getEndTime());

	    int result = attendanceService.updateAttendance(attendanceVO);
	    //System.out.println("update result = " + result);

	    return "redirect:/attendance/statusUpdate?userId=" + attendanceVO.getUserId();
	}
	
	



}
