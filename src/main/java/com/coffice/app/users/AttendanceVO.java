package com.coffice.app.users;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttendanceVO {
	
	private Integer attendanceId;
	private Date attendanceDate;
	private Date  startTime;
	private Date endTime;
	private String status;
	private Integer durationTime;
	private String userId;

}
