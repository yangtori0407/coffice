package com.coffice.app.users;

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
	private Date  startTime;
	private Date endTime;
	private String status;
	private Long durationTime;
	private String userId;

}
