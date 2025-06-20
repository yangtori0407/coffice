package com.coffice.app.attendance;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AttendanceDAO {
	
	public int checkIn(AttendanceVO attendanceVO) throws Exception;
	
	public int checkOut(AttendanceVO attendanceVO) throws Exception;
	
	public AttendanceVO todayStatus(@Param("userId") String userId, @Param("attendanceDate") LocalDate date) throws Exception;

}
