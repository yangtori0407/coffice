package com.coffice.app.chat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffice.app.chat.vo.ChatAddVO;
import com.coffice.app.chat.vo.ChatRoomVO;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatService {

	@Autowired
	private ChatDAO chatDAO;

	public List<ChatRoomVO> getList(UserVO userVO) throws Exception {
		userVO.setUserId("test1"); //로그인 되면 이거 지우기!!!!!!!!
		return chatDAO.getList(userVO);
	}

	public Map<String, Object> addChat(ChatAddVO chatAddVO, UserVO userVO) throws Exception {
		// -1 => 동일한 사용자와 만든 방이 있다.
		// 인원수 => 방 만들어짐
		Map<String, Object> result = new HashMap<>();
		
		userVO.setUserId("test1");
		List<String> list = chatDAO.getCreatorRoomList(userVO);
		String num = checkRoom(list, chatAddVO.getUsers());
		if(num != null) {
			result.put("flag", -1);
			result.put("chatRoomNum", num);
			return result;
		}
		
		ChatRoomVO chatRoomVO = new ChatRoomVO();
		String chatNum = UUID.randomUUID().toString();
		chatRoomVO.setChatRoomName(chatAddVO.getName());
		chatRoomVO.setChatRoomNum(chatNum);
		chatRoomVO.setChatRoomCreator(userVO.getUserId());
		//chatRoomVO.setChatRoomCreator(userVO.getUserId());  //나중에 로그인 되면 주석 풀기
		int r = chatDAO.addChat(chatRoomVO);
		chatAddVO.setChatRoomNum(chatNum);

		r = chatDAO.addUser(chatAddVO);
		if (r == chatAddVO.getUsers().size()) { 
			result.put("flag", r);
			result.put("chatRoomNum", chatNum);
			return result;
		}
		result.put("flag", 0);
		result.put("chatRoomNum", "");
		
		return result;
	}
	
	private String checkRoom(List<String> roomList, List<String> selectUsers) throws Exception{
		for(String room : roomList) {
			List<String> users = chatDAO.getRoomUsers(room);
			boolean isSame = users.containsAll(selectUsers);
			if(isSame) return room;
		}
		return null;
	}

	public String getChatName(ChatRoomVO chatRoomVO) throws Exception{
		// TODO Auto-generated method stub
		return chatDAO.getChatName(chatRoomVO);
	}

}
