package com.coffice.app.posts.notice;

import java.sql.Date;
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
	private Date date;
	
	private List<NoticeFilesVO> files;
}
