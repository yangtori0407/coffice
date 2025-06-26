package com.coffice.app.events.holidays;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HolidayDAO {
	
	public int addHoliday(HolidayVO holidayVO) throws Exception;
	public List<HolidayVO> getHolidays() throws Exception;
	public int deleteAll() throws Exception;

}
