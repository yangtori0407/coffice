package com.coffice.app.approval_lines;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApprovalLineVO {
	
	
	private Long a_line_id;
	private Long document_id;
	private String user_id;
	private Long step_order;
	private String status;
	private Date handling_time;
	private Long sign_id;
	private String reject_reason;
	

}
