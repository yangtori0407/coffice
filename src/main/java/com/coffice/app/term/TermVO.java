package com.coffice.app.term;

import java.sql.Date;

import lombok.Data;

@Data
public class TermVO {
	
	private String userId;
	private Date startTime;
	private Date endTime;

}
