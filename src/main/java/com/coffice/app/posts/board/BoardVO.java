package com.coffice.app.posts.board;

import java.sql.Date;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BoardVO{
	private Long boardNum;
	private String boardTitle;
	private String userId;
	private Long boardHit;
	private String boardContents;
	private Date boardDate;
	private Integer deleteStatus;
	
	private List<CommentVO> comments;
	private Long commentCount;
}
