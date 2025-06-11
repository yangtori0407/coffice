package com.coffice.app.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coffice.app.events.schedule.ScheduleService;
import com.coffice.app.events.schedule.ScheduleVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/events/*")
public class EventController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@GetMapping("schedule")
	public String getSchedule(Model model) {
		model.addAttribute("kind", "일정");
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

}
