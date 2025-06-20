package com.coffice.app.attendance;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttendanceDAO {
	
	public int checkIn(AttendanceVO attendanceVO) throws Exception;
	
	public int checkOut(AttendanceVO attendanceVO) throws Exception;
	
	public AttendanceVO todayStatus(String userId) throws Exception;

}
