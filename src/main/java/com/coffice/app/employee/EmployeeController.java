package com.coffice.app.employee;

import java.time.LocalDate;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coffice.app.attendance.AttendanceService;
import com.coffice.app.page.Pager;
import com.coffice.app.users.UserService;
import com.coffice.app.users.UserVO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/employee/*")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private UserService userService;
	@Autowired
	private AttendanceService attendanceService;
	
	@GetMapping("list")
	public String list(Pager pager, Model model) throws Exception {
		
	    LocalDate now = LocalDate.now();
	    LocalDate startDate = now.withDayOfMonth(1);  // 이번 달 1일
	    LocalDate endDate = now;                      // 오늘 날짜
		
		List<EmployeeVO> employeeList = employeeService.getEmployeesAttendancePercentage(pager, startDate, endDate);
		
		// 직원별 정상근무 퍼센트 계산해서 Map에 담기
	    Map<String, Double> percentMap = new HashMap<>();
	    for (EmployeeVO e : employeeList) {
	        double percent = attendanceService.calculateNormalPercent(e.getUserId());
	        percentMap.put(e.getUserId(), percent);
	    }
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("percentMap", percentMap);
		model.addAttribute("pager", pager);
		model.addAttribute("employee", "list");
		model.addAttribute("kind", "사원 정보 목록");
		
		return "employee/list";
		
	}
	
	@GetMapping("detail")
	public String detail(@RequestParam String userId, Model model) throws Exception{
		EmployeeVO employee = employeeService.getEmployeeById(userId); // or getEmployeeDetail(userId)
	    model.addAttribute("employeeVO", employee);
	    model.addAttribute("kind", "사원 상세정보");
	    return "employee/detail"; 
	}
	
	@GetMapping("update")
	public String update(@RequestParam String userId, Model model) throws Exception {
		EmployeeVO employee = employeeService.getEmployeeById(userId);
	    model.addAttribute("employeeVO", employee);
	    model.addAttribute("kind", "사원정보 수정");
	    
	    return "employee/update";
	}
	
	@PostMapping("update")
	public String update(@ModelAttribute("employeeVO") EmployeeVO employeeVO, @RequestParam("file") MultipartFile file,
						RedirectAttributes redirectAttributes) throws Exception {
		//System.out.println("!!넘어온 deptId: " + employeeVO.getDeptId());
		
		employeeService.updateEmployee(employeeVO, file);
		
		redirectAttributes.addFlashAttribute("msg", "사원 정보가 수정되었습니다!");
		
		return "redirect:/employee/list";
	}

}
