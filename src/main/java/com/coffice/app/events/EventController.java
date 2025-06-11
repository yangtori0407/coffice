package com.coffice.app.events;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.events.holidays.HolidayService;
import com.coffice.app.events.holidays.HolidayVO;

@Controller
@RequestMapping("/events/*")
public class EventController {
	
	@Autowired
	private HolidayService holidayService;
	
	@GetMapping("schedule")
	public String getSchedule(Model model) {
		model.addAttribute("kind", "일정 관리");
		return "events/schedule";
	}
	
	@GetMapping("vacation")
	public String getVacation(Model model) {
		model.addAttribute("kind", "휴가 관리");
		return "events/schedule";
	}
	
	@GetMapping("getHolidays")
	@ResponseBody
	public List<HolidayVO> getHolidays() throws Exception {
		return holidayService.getHolidays();
	}

}
