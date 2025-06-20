package com.coffice.app.attendance;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
	
	@Autowired
	private AttendanceDAO attendanceDAO;
	
	public boolean checkIn(String userId) throws Exception{
		AttendanceVO attendanceVO = new AttendanceVO();
		attendanceVO.setUserId(userId);
		attendanceVO.setAttendanceDate(new Date(System.currentTimeMillis()));
		attendanceVO.setStartTime(LocalDateTime.now());
		return attendanceDAO.checkIn(attendanceVO) > 0;
	}
	
	public boolean checkOut(String userId) throws Exception{
		AttendanceVO attendanceVO = new AttendanceVO();
		attendanceVO.setUserId(userId);
		attendanceVO.setEndTime(LocalDateTime.now());
		return attendanceDAO.checkOut(attendanceVO) > 0;
	}
	
	public AttendanceVO todayStatus(String userId) throws Exception {
		return attendanceDAO.todayStatus(userId);
	}

}
