package com.coffice.app.events;


import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EventVO {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-ddHH:mm")
	private Date startTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-ddHH:mm")
	private Date endTime;
	private String userId;
	private Date insertTime;
	private String editor;
	private Date editTime;
	private boolean deleteStatus;

}
