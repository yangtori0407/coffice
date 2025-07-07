package com.coffice.app.attendance;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	            
	            long durationSeconds = Duration.between(attendance.getStartTime(), now).getSeconds();
	            attendance.setDurationTime(durationSeconds);


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
	
	private static final long WEEKLY_REQUIRED_MINUTES = 40 * 60; // 주 40시간

	
	public Map<String, Long> getWeeklyWorkStatus(String userId) throws Exception{
		Long workedSeconds = attendanceDAO.getWeeklyWorkDuration(userId);
		long standardSeconds = 40 * 60 * 60;
		
		long remaining = Math.max(standardSeconds - workedSeconds, 0);
		long overtime = Math.max(workedSeconds - standardSeconds, 0);

	    Map<String, Long> result = new HashMap<>();
	    result.put("workedSeconds", workedSeconds);
	    result.put("remainingSeconds", remaining);
	    result.put("overtimeSeconds", overtime);
		
		return result;
	}


	
	public void insertAndUpdateAbsences() throws Exception {
		
		LocalDate yesterday = LocalDate.now().minusDays(1);
		
		DayOfWeek dayOfWeek = yesterday.getDayOfWeek();
	    if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
	        //System.out.println("❌ 어제는 주말(" + dayOfWeek + ")이므로 결근 처리 스킵");
	        return;
	    }
		
	    // 1. 출근 기록 자체가 없는 사람들 → INSERT 결근
	    List<String> absentUserIds = attendanceDAO.getAbsentUserIds();
	    for (String userId : absentUserIds) {
	    	
	    	boolean alreadyExists = attendanceDAO.existsAttendance(userId, yesterday);
	        if (alreadyExists) {
	            
	            continue; // skip insert
	        }
	    	
	        AttendanceVO attendance = new AttendanceVO();
	        attendance.setUserId(userId);
	        attendance.setStartTime(null);
	        attendance.setEndTime(null);
	        attendance.setStatus("결근");
	        attendance.setAttendanceDate(yesterday);
	        attendanceDAO.insertAbsence(attendance);
	    }

	    // 2. 출근했지만 퇴근 안 한 사람들 → UPDATE status 결근
	    List<AttendanceVO> unclosedList = attendanceDAO.getTodayUnfinishedAttendances();
	    for (AttendanceVO attendance : unclosedList) {
	        attendance.setStatus("결근");
	        attendance.setEndTime(LocalDateTime.of(yesterday, LocalTime.MIDNIGHT));  
	        attendanceDAO.updateStatus(attendance);
	    }
	}
	
	public List<AttendanceVO> getAttendanceListByUser(String userId) throws Exception {
	    return attendanceDAO.getAttendanceByUser(userId);
	}
	
	public double calculateNormalPercent(String userId) throws Exception {
        List<AttendanceVO> allList = attendanceDAO.getAttendanceByUser(userId);

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        
        // 이번 달 평일(근무일) 수 계산
        long totalWorkingDays = firstDayOfMonth.datesUntil(today.plusDays(1))
                .filter(d -> {
                    DayOfWeek day = d.getDayOfWeek();
                    return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
                })
                .count();

            // 정상근무 일 수만 필터링
            long normal = allList.stream()
                .filter(a -> {
                    LocalDate date = a.getAttendanceDate();
                    return !date.isBefore(firstDayOfMonth) && !date.isAfter(today);
                })
                .filter(a -> "정상근무".equals(a.getStatus()))
                .map(AttendanceVO::getAttendanceDate)  // 날짜 중복 제거용
                .distinct()
                .count();

            return totalWorkingDays == 0 ? 0 : (double) normal / totalWorkingDays * 100;
    }

	public int updateAttendance(AttendanceVO attendanceVO) throws Exception{
		
		if (attendanceVO.getStartTime() != null && attendanceVO.getEndTime() != null) {
		    Duration duration = Duration.between(attendanceVO.getStartTime(), attendanceVO.getEndTime());
		    attendanceVO.setDurationTime(duration.getSeconds()); // 초 단위 저장
		}

		return attendanceDAO.updateAttendance(attendanceVO);
	}
	
	public List<AttendanceStatusCountVO> getMonthlyStatusCount(String userId, int year, int month) {
        return attendanceDAO.getMonthlyStatusCount(userId, year, month);
    }


}
