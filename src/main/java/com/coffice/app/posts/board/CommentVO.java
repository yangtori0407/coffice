package com.coffice.app.posts.board;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommentVO {
	
	private Long commentNum;
	private Long boardNum;
	private String userId;
	private Timestamp commentDate;
	private String commentContents;
	private Long commentRef;
	private Long commentStep;
	private Long commentDepth;
	
	
	public String getFormatted() {
		LocalDateTime localDateTime = commentDate.toLocalDateTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
		return localDateTime.format(formatter);
	}
}
