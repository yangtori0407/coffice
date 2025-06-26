package com.coffice.app.events.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffice.app.users.UserVO;

@Service
public class ScheduleService {

	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	public int addSchedule(ScheduleVO scheduleVO) throws Exception {
		int result = 0;
		if(scheduleVO.getRepeatType() == null) {
			result = scheduleDAO.addSchedule(scheduleVO);
		}else {
			result = scheduleDAO.addRepeatSchedule(scheduleVO);
		}
		return result; 
	}
	
	public List<ScheduleVO> getAll(UserVO userVO) throws Exception {
		return scheduleDAO.getAll(userVO);
	}
	
	public ScheduleVO getSchedule(ScheduleVO scheduleVO) throws Exception {
		return scheduleDAO.getSchedule(scheduleVO);
	}
	
	public List<ScheduleVO> getRepeatSchedules(UserVO userVO) throws Exception {
		return scheduleDAO.getRepeatSchedules(userVO);
	}
	
	public int updateSchedule(ScheduleVO scheduleVO) throws Exception {
		int result = 0;
		if(scheduleVO.isException()) {
			scheduleDAO.addException(scheduleVO);
			scheduleVO.setRepeatId(scheduleVO.getExceptions().get(0).getRepeatId());
			result = scheduleDAO.addSchedule(scheduleVO);
		}else if(scheduleVO.getRepeatId() != null){
			result = scheduleDAO.updateRepeatSchedule(scheduleVO);
		}else {			
			result = scheduleDAO.updateSchedule(scheduleVO);
		}
		return result;
	}
	
	public int deleteSchedule(ScheduleVO scheduleVO) throws Exception {
		int result = 0;
		
		if(scheduleVO.isException()) {
			result = scheduleDAO.addException(scheduleVO);
		}else if(scheduleVO.getRepeatId() != null) {
			result = scheduleDAO.deleteRepeatSchedule(scheduleVO);
		}else {
			result = scheduleDAO.deleteSchedule(scheduleVO);
		}
		
		return result;
	}
	
	public int dragDrop(ScheduleVO scheduleVO) throws Exception {
		return scheduleDAO.dragDrop(scheduleVO);
	}

}
