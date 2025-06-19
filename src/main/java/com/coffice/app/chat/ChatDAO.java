package com.coffice.app.chat;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.chat.vo.ChatAddVO;
import com.coffice.app.chat.vo.ChatContentsVO;
import com.coffice.app.chat.vo.ChatFilesVO;
import com.coffice.app.chat.vo.ChatPersonVO;
import com.coffice.app.chat.vo.ChatRoomVO;
import com.coffice.app.files.FileVO;
import com.coffice.app.posts.notice.NoticeFilesVO;
import com.coffice.app.users.UserVO;

@Mapper
public interface ChatDAO {

	List<ChatRoomVO> getRoomList() throws Exception;

	int addChat(ChatRoomVO chatRoomVO) throws Exception;

	int addUser(ChatAddVO chatAddVO) throws Exception;


	List<String> getRoomUsers(String room) throws Exception;

	List<ChatRoomVO> getList(UserVO userVO) throws Exception;

	ChatRoomVO getChatInfo(ChatRoomVO chatRoomVO) throws Exception;

	int addContents(ChatContentsVO chatContentsVO) throws Exception;

	ChatContentsVO getContentsInfo(ChatContentsVO chatContentsVO) throws Exception;

	List<ChatContentsVO> getChatContentsList(ChatRoomVO chatRoomVO) throws Exception;

	String checkDuplication(ChatAddVO chatAddVO) throws Exception;

	String getUserInfo(ChatContentsVO chatContentsVO) throws Exception;

	int addFileContents(ChatContentsVO chatContentsVO) throws Exception;

	int addFile(ChatFilesVO chatFilesVO) throws Exception;

	ChatContentsVO getChatContentsInfo(Long chatNum) throws Exception;

	FileVO getFileDetail(NoticeFilesVO filesVO) throws Exception;

	List<ChatPersonVO> getChatUserInfo(String chatRoomNum) throws Exception;

	List<UserVO> getChatUsersDetail(String chatRoomNum) throws Exception;

	int updateLastReadAt(Map<String, Object> info) throws Exception;

	Integer getChatAlarm(Map<String, Object> info) throws Exception;

	int updateAlarm(Map<String, Object> info) throws Exception;

	List<ChatContentsVO> getChatMore(Map<String, Object> info) throws Exception;
	
}
