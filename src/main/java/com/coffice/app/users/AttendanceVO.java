package com.coffice.app.users;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttendanceVO {
	
	private Integer attendanceId;
	private Date attendanceDate;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String status;
	private Integer durationTime;
	private String userId;

}
