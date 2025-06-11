package com.coffice.app.events.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	public int addSchedule(ScheduleVO scheduleVO) throws Exception {
		return scheduleDAO.addSchedule(scheduleVO); 
	}

}
