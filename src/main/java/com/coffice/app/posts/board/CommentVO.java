package com.coffice.app.posts.board;

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
	private String commentContents;
	private Long commentRef;
	private Long commentStep;
	private Long commentDepth;
}
