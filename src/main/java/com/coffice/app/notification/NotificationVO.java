package com.coffice.app.notification;

import java.sql.Timestamp;
import java.util.List;

import com.coffice.app.users.UserVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotificationVO {
	private Long notiNum;
	private String notiKind;
	private String notiContents;
	private Timestamp notiDate;
	private String relateEntity;
	private Long relateId;
	
	//DB 컬럼에는 없음! select 할 때 사용하는 용도
	private int notiCheckStatus;
	private int notiCheckNum;
}
