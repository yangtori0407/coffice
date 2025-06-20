package com.coffice.app.attendance;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StatusCalculator {
	
	public static String detemineStatus(LocalDateTime start, LocalDateTime end) throws Exception {
		if (start == null || end == null) return "결근";
		
		long workMinutes = Duration.between(start, end).toMinutes();
		LocalTime startTime = start.toLocalTime();
		LocalTime endTime = end.toLocalTime();
		
		if(startTime.isBefore(LocalTime.of(9, 0))&&
			endTime.isAfter(LocalTime.of(18, 0)) &&
			workMinutes >= 480) {
			return "정상근무";
		}
		
		if(!startTime.isBefore(LocalTime.of(12, 0)) && 
			startTime.isBefore(LocalTime.of(13, 0))) {
			return endTime.isBefore(LocalTime.of(17, 0)) ? "결근" : "조퇴";
		}
		
		if (startTime.isBefore(LocalTime.of(12, 0))) {
            return (workMinutes < 300) ? "결근" : "조퇴";
        }
		
		if (!startTime.isBefore(LocalTime.of(13, 0))) {
            return (workMinutes < 240) ? "결근" : "지각";
        }
		
		return "결근";
	}
	
	

}
