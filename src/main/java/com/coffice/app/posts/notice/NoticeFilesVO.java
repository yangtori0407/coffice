package com.coffice.app.posts.notice;

import com.coffice.app.files.FileVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NoticeFilesVO extends FileVO{

	private Long noticeNum;
}
