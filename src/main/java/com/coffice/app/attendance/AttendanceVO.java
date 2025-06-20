package com.coffice.app.attendance;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttendanceVO {
	
	private Long attendanceId;
	private Date attendanceDate;
	private LocalDateTime  startTime;
	private LocalDateTime endTime;
	private String status;
	private Long durationTime;
	private String userId;

}
