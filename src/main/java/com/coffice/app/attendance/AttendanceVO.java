package com.coffice.app.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttendanceVO {
	
	private Long attendanceId;
	private LocalDate attendanceDate;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String status;
	private Long durationTime;
	private String userId;
	private String reason;
	
	//jsp 시간만 띄우기 위함
	private String startTimeStr;
	private String endTimeStr;
	
	// 폼 입력용 필드
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTimeInput;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTimeInput;


}
