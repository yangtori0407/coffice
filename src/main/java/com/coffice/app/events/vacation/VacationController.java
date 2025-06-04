package com.coffice.app.events.vacation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events/*")
public class VacationController {
	
	@GetMapping("vacation")
	public void getVacations() throws Exception {
		
	}

}
