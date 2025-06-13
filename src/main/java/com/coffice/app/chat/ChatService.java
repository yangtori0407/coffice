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
import com.coffice.app.chat.vo.ChatContentsVO;
import com.coffice.app.chat.vo.ChatRoomVO;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatService {

	@Autowired
	private ChatDAO chatDAO;

	public List<ChatRoomVO> getList(UserVO userVO) throws Exception {
		//userVO.setUserId("test1"); //로그인 되면 이거 지우기!!!!!!!!
		return chatDAO.getList(userVO);
	}

	public Map<String, Object> addChat(ChatAddVO chatAddVO, UserVO userVO) throws Exception {
		// -1 => 동일한 사용자와 만든 방이 있다.
		// 인원수 => 방 만들어짐
		//log.info("중복 확인 : {}", chatAddVO);
		
		Map<String, Object> result = new HashMap<>();
		
		chatAddVO.getUsers().add(userVO.getUserId());
		chatAddVO.setSize(chatAddVO.getUsers().size());
		String duplication = chatDAO.checkDuplication(chatAddVO);
		//log.info("중복 확인 : {}", duplication);
		if(duplication != null) {
			result.put("flag", -1);
			result.put("chatRoomNum", duplication);
			return result;
		}
		
		
//		ChatRoomVO chatRoomVO = new ChatRoomVO();
//		String chatNum = UUID.randomUUID().toString();
//		chatRoomVO.setChatRoomName(chatAddVO.getName());
//		chatRoomVO.setChatRoomNum(chatNum);
//		chatRoomVO.setChatRoomCreator(userVO.getUserId());
//		int r = chatDAO.addChat(chatRoomVO);
//		chatAddVO.setChatRoomNum(chatNum);
//		
//		r = chatDAO.addUser(chatAddVO);
//		
//		if(r > 0) {
//			result.put("flg", 1);
//			result.put("chatRoomNum", chatNum);
//			return result;
//		}
//		
//		result.put("flg", 0);
		
		return result;
				
	}
	
	

	public ChatRoomVO getChatInfo(ChatRoomVO chatRoomVO) throws Exception{
		// TODO Auto-generated method stub
		return chatDAO.getChatInfo(chatRoomVO);
	}

	public ChatContentsVO addContents(ChatContentsVO chatContentsVO, UserVO userVO) throws Exception{
		chatContentsVO.setSender(userVO.getUserId());
		log.info("sender name : {}", chatContentsVO);
		chatDAO.addContents(chatContentsVO);
		chatContentsVO = chatDAO.getContentsInfo(chatContentsVO);
		chatContentsVO.setName(chatDAO.getUserInfo(chatContentsVO));
		
		return chatContentsVO;
	}

	public List<ChatContentsVO> getChatContentsList(ChatRoomVO chatRoomVO) throws Exception{
		// TODO Auto-generated method stub
		return chatDAO.getChatContentsList(chatRoomVO);
	}

}
