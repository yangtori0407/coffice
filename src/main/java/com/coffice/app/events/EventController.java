package com.coffice.app.events;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.events.holidays.HolidayService;
import com.coffice.app.events.holidays.HolidayVO;
import com.coffice.app.events.schedule.ScheduleService;
import com.coffice.app.events.schedule.ScheduleVO;
import com.coffice.app.events.vacation.VacationService;
import com.coffice.app.events.vacation.VacationVO;
import com.coffice.app.notification.NotificationService;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/events/*")
public class EventController {
	
	@Autowired
	private ScheduleService scheduleService;

    @Autowired
	private HolidayService holidayService;
    
    @Autowired
    private VacationService vacationService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private EventUtility eventUtility;
	
	@GetMapping("schedule")
	public String schedule(Model model) throws Exception {
		model.addAttribute("kind", "일정");
		model.addAttribute("events", "schedule");
		return "events/schedule";
	}
	
//	@GetMapping("getSchedules")
//	@ResponseBody
//	public List<ScheduleVO> getAll(Authentication authentication) throws Exception {
//		UserVO userVO = (UserVO)authentication.getPrincipal();
//		return scheduleService.getAll(userVO);
//	}
//	
//	@GetMapping("getRepeatSchedules")
//	@ResponseBody
//	public List<ScheduleVO> getRepeatSchedules(Authentication authentication) throws Exception {
//		UserVO userVO = (UserVO)authentication.getPrincipal();
//		return scheduleService.getRepeatSchedules(userVO);
//	}
	
	@GetMapping("getSchedule")
	@ResponseBody
	public ScheduleVO getSchedule(ScheduleVO scheduleVO) throws Exception {
		return scheduleService.getSchedule(scheduleVO);
	}
	
	@GetMapping("vacation")
	public String vacation(Model model) throws Exception {
		model.addAttribute("kind", "휴가");
		model.addAttribute("events", "vacation");
		return "events/schedule";
	}
	
	
	@PostMapping("schedule/add")
	public String addSchedule(ScheduleVO scheduleVO, Authentication authentication) throws Exception {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		scheduleVO.setUserId(userVO.getUserId());
		int result =  scheduleService.addSchedule(scheduleVO);			
		log.info("{}, {}", result, scheduleVO);
		return "events/schedule";
	}
	
	@PostMapping("schedule/update")
	public String updateSchedule(@ModelAttribute ScheduleVO scheduleVO, Authentication authentication) throws Exception {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		scheduleVO.setEditor(userVO.getUserId());
		scheduleVO.setUserId(userVO.getUserId());
		int result = scheduleService.updateSchedule(scheduleVO);
		log.info("{}, {}", result, scheduleVO);
//		log.info("{}", scheduleVO.getExceptions().get(0));
		return "events/schedule";
	}
	
	@PostMapping("schedule/delete")
	public String deleteSchedule(ScheduleVO scheduleVO) throws Exception {
		scheduleVO.setEditor("scheduleTest");
		scheduleVO.setUserId("scheduleTest");
		int result = scheduleService.deleteSchedule(scheduleVO);
		log.info("{}, {}", result, scheduleVO);
		return "events/schedule";
	}
	
//	@GetMapping("getHolidays")
//	@ResponseBody
//	public List<HolidayVO> getHolidays() throws Exception {
//		return holidayService.getHolidays();
//	}
	
	@GetMapping("getDepsUsers")
	@ResponseBody
	public List<UserVO> getDepsUsers(Authentication authentication) throws Exception {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		return vacationService.getDepsUsers(userVO);
	}
	
	@PostMapping("vacation/apply")
	public String applyForLeave(VacationVO vacationVO, Authentication authentication) throws Exception {
		vacationService.applyForLeave(vacationVO, authentication);
		return "events/vacation";
	}
	
	@GetMapping("vacation/applyList")
	@ResponseBody
	public List<VacationVO> getApplyList(Authentication authentication) throws Exception {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		return vacationService.getApplyList(userVO);
	}
	
	@GetMapping("vacation/acceptList")
	@ResponseBody
	public List<VacationVO> getAcceptList(Authentication authentication) throws Exception {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		return vacationService.getAcceptList(userVO);
	}
	
	@GetMapping("vacation/getOne")
	@ResponseBody
	public Map<String, Object> getOne(VacationVO vacationVO) throws Exception {
		return vacationService.getOne(vacationVO);
	}
	
	@PostMapping("vacation/approve")
	public void approve(VacationVO vacationVO, Authentication authentication) throws Exception {
		int result = vacationService.approve(vacationVO, authentication);
		log.info("approveComplete : {}", result);
	}
	
	@PostMapping("vacation/reject")
	public void reject(VacationVO vacationVO, Authentication authentication) throws Exception {
		int result = vacationService.reject(vacationVO, authentication);
		log.info("rejectComplete : {}", result);
	}
	
	@GetMapping("vacation/getList")
	@ResponseBody
	public List<VacationVO> getList(Authentication authentication) throws Exception {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		return vacationService.getList(userVO);
	}
	
	@PostMapping("schedule/dragDrop")
	public String dragDrop(ScheduleVO scheduleVO, Authentication authentication, Model model) throws Exception {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		scheduleVO.setEditor(userVO.getUserId());
		int result = scheduleService.dragDrop(scheduleVO);
		model.addAttribute("result", result);
		return "commons/ajaxResult";
	}
	
	@PostMapping("vacation/update")
	public String updateApply(VacationVO vacationVO, Authentication authentication, Model model) throws Exception {
		
		int result = vacationService.updateApply(vacationVO, authentication);
		model.addAttribute("result", result);
		return "commons/ajaxResult";
	}
	
	@PostMapping("vacation/cancel")
	public String cancel(VacationVO vacationVO, Authentication authentication, Model model) throws Exception {
		
		int result = vacationService.cancel(vacationVO, authentication);
		model.addAttribute("result", result);
		return "commons/ajaxResult";
	}
	
	@GetMapping("all")
	@ResponseBody
	public List<CalendarEventDTO> getAllEvents(@RequestParam String kind, @AuthenticationPrincipal UserVO user) throws Exception {
	    List<CalendarEventDTO> result = new ArrayList<>();

	    // 공휴일
	    List<HolidayVO> holidays = holidayService.getHolidays();
	    holidays.forEach(h -> result.add(eventUtility.fromHoliday(h)));
	    
	    if("schedule".equals(kind)) {
	    	// 일반 일정
	    	List<ScheduleVO> schedules = scheduleService.getAll(user);
	    	schedules.forEach(s -> result.add(eventUtility.fromSchedule(s, user.getUserId(), "#0288d1", "#bdbdbd")));
	    	
	    	// 반복 일정
	    	List<ScheduleVO> repeatSchedules = scheduleService.getRepeatSchedules(user);
	    	repeatSchedules.forEach(r -> result.add(eventUtility.fromSchedule(r, user.getUserId(), "#0288d1", "#bdbdbd")));	    	
	    }else if("vacation".equals(kind)) {
	    	// 휴가
	    	List<VacationVO> vacations = vacationService.getList(user);
	    	vacations.forEach(v -> result.add(eventUtility.fromVacation(v, user.getUserId(), "#43a047", "#bdbdbd")));	    	
	    }

	    return result;
	}

}
