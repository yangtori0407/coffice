package com.coffice.app.notification;

import java.sql.Timestamp;

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
	private boolean checkStaus;
	private Timestamp notiDate;
	private String relateEntity;
	private Long relateId;
	private String receiver;
	private String sender;
}
