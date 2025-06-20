package com.coffice.app.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
	
	@Autowired
	private AttendanceDAO attendanceDAO;
	
	public boolean checkIn(String userId) throws Exception{
		try {
	        AttendanceVO attendance = new AttendanceVO();
	        attendance.setUserId(userId);
	        attendance.setAttendanceDate(LocalDate.now());
	        attendance.setStartTime(LocalDateTime.now());
	        attendance.setStatus("근무중"); // 임시 상태
	        attendanceDAO.checkIn(attendance);
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean checkOut(String userId) throws Exception{
		try {
	        AttendanceVO attendance = attendanceDAO.todayStatus(userId, LocalDate.now());

	        if (attendance != null) {
	            LocalDateTime now = LocalDateTime.now();
	            attendance.setEndTime(now);

	            String status = StatusCalculator.determineStatus(attendance.getStartTime(), now);
	            attendance.setStatus(status);

	            attendanceDAO.checkOut(attendance);
	            return true;
	        } else {
	            return false; // 출근 기록 없을 경우
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
		
	}
	
	//출퇴근 기록 전체 
	public AttendanceVO todayStatus(String userId, LocalDate date) throws Exception {
		return attendanceDAO.todayStatus(userId, date);
	}

}
