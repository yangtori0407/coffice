package com.coffice.app.events.schedule;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.users.UserVO;

@Mapper
public interface ScheduleDAO {
	
	public int addSchedule(ScheduleVO scheduleVO) throws Exception;
	public int addRepeatSchedule(ScheduleVO scheduleVO) throws Exception;
	public List<ScheduleVO> getAll(UserVO userVO) throws Exception;
	public ScheduleVO getSchedule(ScheduleVO scheduleVO) throws Exception;
	public List<ScheduleVO> getRepeatSchedules(UserVO userVO) throws Exception;
	public int updateSchedule(ScheduleVO scheduleVO) throws Exception;
	public int addException(ScheduleVO scheduleVO) throws Exception;
	public int updateRepeatSchedule(ScheduleVO scheduleVO) throws Exception;
	public int deleteSchedule(ScheduleVO scheduleVO) throws Exception;
	public int deleteRepeatSchedule(ScheduleVO scheduleVO) throws Exception;
	public int dragDrop(ScheduleVO scheduleVO) throws Exception;

}
