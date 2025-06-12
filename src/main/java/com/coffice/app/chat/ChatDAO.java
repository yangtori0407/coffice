package com.coffice.app.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.chat.vo.ChatAddVO;
import com.coffice.app.chat.vo.ChatContentsVO;
import com.coffice.app.chat.vo.ChatRoomVO;
import com.coffice.app.users.UserVO;

@Mapper
public interface ChatDAO {

	List<ChatRoomVO> getRoomList() throws Exception;

	int addChat(ChatRoomVO chatRoomVO) throws Exception;

	int addUser(ChatAddVO chatAddVO) throws Exception;

	List<String> getCreatorRoomList(UserVO userVO) throws Exception;

	List<String> getRoomUsers(String room) throws Exception;

	List<ChatRoomVO> getList(UserVO userVO) throws Exception;

	ChatRoomVO getChatInfo(ChatRoomVO chatRoomVO) throws Exception;

	int addContents(ChatContentsVO chatContentsVO) throws Exception;

	ChatContentsVO getContentsInfo(ChatContentsVO chatContentsVO) throws Exception;

	List<ChatContentsVO> getChatContentsList(ChatRoomVO chatRoomVO) throws Exception;
	
}
