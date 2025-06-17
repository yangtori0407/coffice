package com.coffice.app.events.schedule;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.fortuna.ical4j.model.Recur;

@Service
public class ScheduleService {

	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	public int addSchedule(ScheduleVO scheduleVO) throws Exception {
		return scheduleDAO.addSchedule(scheduleVO); 
	}
	
	public int addRepeatSchedule(ScheduleVO scheduleVO) throws Exception {
		
//		String rrule = "rrule";
//		
//		if(scheduleVO.getRepeatCount() != null && scheduleVO.getRepeatCount() != 0) {
//			rrule = "FREQ="+scheduleVO.getRepeatType().toUpperCase()+";COUNT="+scheduleVO.getRepeatCount()+";";
//		}else {
//			Date until = scheduleVO.getRepeatEnd();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//	        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//	        String utc = sdf.format(until);
//			rrule = "FREQ="+scheduleVO.getRepeatType().toUpperCase()+";UNTIL="+utc+";";
//		}
//		
//		Recur recur = new Recur(rrule);
//		
//		System.out.println(rrule);
//		
//		System.out.println(scheduleVO.getDuration());
		
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
