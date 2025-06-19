package com.coffice.app.events.schedule;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleDAO {
	
	public int addSchedule(ScheduleVO scheduleVO) throws Exception;
	public int addRepeatSchedule(ScheduleVO scheduleVO) throws Exception;
	public List<ScheduleVO> getAll() throws Exception;
	public ScheduleVO getSchedule(ScheduleVO scheduleVO) throws Exception;
	public List<ScheduleVO> getRepeatSchedules() throws Exception;
	public int updateSchedule(ScheduleVO scheduleVO) throws Exception;
	public int addException(ScheduleVO scheduleVO) throws Exception;
	public int updateRepeatSchedule(ScheduleVO scheduleVO) throws Exception;
	public int deleteSchedule(ScheduleVO scheduleVO) throws Exception;
	public int deleteRepeatSchedule(ScheduleVO scheduleVO) throws Exception;

}
