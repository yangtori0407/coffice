package com.coffice.app.events;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EventVO {
	
	@DateTimeFormat(pattern = "yyyy-MM-ddHH:mm")
	private Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-ddHH:mm")
	private Date endTime;
	private String userId;
	private Date insertTime;
	private String editor;
	private Date editTime;
	private boolean deleteStatus;

}
