package com.coffice.app.events.schedule;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleDAO {
	
	public int addSchedule(ScheduleVO scheduleVO) throws Exception;
	public int addRepeatSchedule(ScheduleVO scheduleVO) throws Exception;

}
