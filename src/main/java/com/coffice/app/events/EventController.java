package com.coffice.app.events;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events/*")
public class EventController {
	
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

}
