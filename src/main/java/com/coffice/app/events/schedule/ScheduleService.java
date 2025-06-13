package com.coffice.app.events.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	public int addSchedule(ScheduleVO scheduleVO) throws Exception {
		return scheduleDAO.addSchedule(scheduleVO); 
	}
	
	public int addRepeatSchedule(ScheduleVO scheduleVO) throws Exception {
		return scheduleDAO.addRepeatSchedule(scheduleVO);
	}
	
	public List<ScheduleVO> getAll() throws Exception {
		return scheduleDAO.getAll();
	}
	
	public ScheduleVO getSchedule(ScheduleVO scheduleVO) throws Exception {
		return scheduleDAO.getSchedule(scheduleVO);
	}
	
	public List<ScheduleVO> getRepeatSchedules() throws Exception {
		return scheduleDAO.getRepeatSchedules();
	}

}
