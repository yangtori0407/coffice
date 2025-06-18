package com.coffice.app.events;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.events.holidays.HolidayService;
import com.coffice.app.events.holidays.HolidayVO;
import com.coffice.app.events.schedule.ScheduleService;
import com.coffice.app.events.schedule.ScheduleVO;
import com.coffice.app.events.vacation.VacationVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/events/*")
public class EventController {
	
	@Autowired
	private ScheduleService scheduleService;

    @Autowired
	private HolidayService holidayService;

	
	@GetMapping("schedule")
	public String schedule(Model model) throws Exception {
		model.addAttribute("kind", "일정");
		return "events/schedule";
	}
	
	@GetMapping("getSchedules")
	@ResponseBody
	public List<ScheduleVO> getAll() throws Exception {
		return scheduleService.getAll();
	}
	
	@GetMapping("getRepeatSchedules")
	@ResponseBody
	public List<ScheduleVO> getRepeatSchedules() throws Exception {
		return scheduleService.getRepeatSchedules();
	}
	
	@GetMapping("getSchedule")
	@ResponseBody
	public ScheduleVO getSchedule(ScheduleVO scheduleVO) throws Exception {
		return scheduleService.getSchedule(scheduleVO);
	}
	
	@GetMapping("vacation")
	public String vacation(Model model) {
		model.addAttribute("kind", "휴가");
		return "events/schedule";
	}
	
	
	@PostMapping("schedule/add")
	public String addSchedule(ScheduleVO scheduleVO) throws Exception {
		scheduleVO.setUserId("scheduleTest");
		int result = 0;
		if(scheduleVO.getRepeatType() == null) {
			result = scheduleService.addSchedule(scheduleVO);			
		}else {
			result = scheduleService.addRepeatSchedule(scheduleVO);
		}
		log.info("{}, {}", result, scheduleVO);
		return "events/schedule";
	}
	
	@PostMapping("schedule/update")
	public String updateSchedule(@ModelAttribute ScheduleVO scheduleVO) throws Exception {
		scheduleVO.setEditor("scheduleTest");
		scheduleVO.setUserId("scheduleTest");
		int result = scheduleService.updateSchedule(scheduleVO);
		log.info("{}, {}", result, scheduleVO);
//		log.info("{}", scheduleVO.getExceptions().get(0));
		return "events/schedule";
	}
	
	@GetMapping("getHolidays")
	@ResponseBody
	public List<HolidayVO> getHolidays() throws Exception {
		return holidayService.getHolidays();
	}

}
