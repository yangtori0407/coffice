package com.coffice.app.events;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.events.holidays.HolidayService;
import com.coffice.app.events.holidays.HolidayVO;

import com.coffice.app.events.schedule.ScheduleService;
import com.coffice.app.events.schedule.ScheduleVO;

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
	public String getSchedule(Model model) throws Exception {
		model.addAttribute("kind", "일정");
		model.addAttribute("schedules", scheduleService.getSchedule());
		return "events/schedule";
	}
	
	@GetMapping("vacation")
	public String getVacation(Model model) {
		model.addAttribute("kind", "휴가");
		return "events/schedule";
	}
	
	@PostMapping("schedule/add")
	public String addSchedule(ScheduleVO scheduleVO) throws Exception {
		scheduleVO.setUserId("addTest");
		int result = scheduleService.addSchedule(scheduleVO);
		log.info("{}, {}", result, scheduleVO);
		return "events/schedule";
	}
	
	@GetMapping("getHolidays")
	@ResponseBody
	public List<HolidayVO> getHolidays() throws Exception {
		return holidayService.getHolidays();
	}

}
