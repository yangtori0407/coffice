package com.coffice.app.events.schedule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events/*")
public class ScheduleController {
	
	@GetMapping("schedule")
	public void getSchedule() throws Exception {
		
	}

}
