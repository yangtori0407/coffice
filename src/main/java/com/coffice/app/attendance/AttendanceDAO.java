package com.coffice.app.attendance;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coffice.app.page.Pager;

@Mapper
public interface AttendanceDAO {
	
	public int checkIn(AttendanceVO attendanceVO) throws Exception;
	
	public int checkOut(AttendanceVO attendanceVO) throws Exception;
	
	public AttendanceVO todayStatus(@Param("userId") String userId, @Param("attendanceDate") LocalDate date) throws Exception;
	
	public Long getWeeklyWorkDuration(String userId) throws Exception;
	
	public int getAttendanceForMonth(@Param("userId") String userId,
							         @Param("startDate") LocalDate startDate,
							         @Param("endDate") LocalDate endDate) throws Exception;

	public List<String> getAbsentUserIds() throws Exception;
	
	public int insertAbsence(AttendanceVO attendanceVO) throws Exception;
	
	public List<AttendanceVO> getTodayUnfinishedAttendances() throws Exception;
	
	public int updateStatus(AttendanceVO attendanceVO) throws Exception;
	
	public Long getTotalCount(Pager pager) throws Exception;
	
	public List<AttendanceVO> getAllAttendances (Pager pager) throws Exception;
	
	public List<AttendanceVO> getAttendanceByUser (String userId) throws Exception;
	
	public int updateAttendance (AttendanceVO attendanceVO) throws Exception;
	
	

}



