package com.coffice.app.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatDAO {

	List<ChatRoomVO> getList() throws Exception;
	
}
