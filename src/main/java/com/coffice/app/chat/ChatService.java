package com.coffice.app.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
	
	@Autowired
	private ChatDAO chatDAO;

	public List<ChatRoomVO> getList() throws Exception{
		return chatDAO.getList();
	}

}
