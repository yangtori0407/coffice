package com.coffice.app.posts.notice;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NoticeVO {
	private Long noticeNum;
	private String userId;
	private String noticeTitle;
	private String noticeContents;
	private Long noticeHit;
	private Timestamp noticeDate;
	private Integer deleteStatus;
	private Date updateDate;
	
	private List<NoticeFilesVO> files;
	
	public String getFormatted() {
		if(getNoticeDate() != null) {
			
			LocalDateTime localDateTime = this.getNoticeDate().toLocalDateTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
			return localDateTime.format(formatter);
		}
		return "";
	}
}
