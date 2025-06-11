package com.coffice.app.events;


import java.util.Date;

import lombok.Data;

@Data
public class EventVO {
	
	private Date startTime;
	private Date endTime;
	private String userId;
	private Date insertTime;
	private String editor;
	private Date editTime;
	
}
