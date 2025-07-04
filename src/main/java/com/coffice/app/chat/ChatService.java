package com.coffice.app.chat;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.chat.vo.ChatAddVO;
import com.coffice.app.chat.vo.ChatContentsVO;
import com.coffice.app.chat.vo.ChatFilesVO;
import com.coffice.app.chat.vo.ChatPersonVO;
import com.coffice.app.chat.vo.ChatRoomVO;
import com.coffice.app.files.FileManager;
import com.coffice.app.files.FileVO;
import com.coffice.app.posts.notice.NoticeFilesVO;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatService {

	@Autowired
	private ChatDAO chatDAO;
	
	@Autowired
	private FileManager fileManager;
	
	@Value("${app.files.base}")
	private String path;

	public List<ChatRoomVO> getList(UserVO userVO) throws Exception {
		
		List<ChatRoomVO> list = chatDAO.getList(userVO);
		
		for(ChatRoomVO l : list) {
			Map<String, Object> info = new HashMap<>();
			info.put("userId", userVO.getUserId());
			info.put("chatRoomNum", l.getChatRoomNum());
			l.setChatAmount(chatDAO.getChatAmount(info));
		}
		
		return list;
	}
	@Transactional
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
		
		
		ChatRoomVO chatRoomVO = new ChatRoomVO();
		String chatNum = UUID.randomUUID().toString();
		chatRoomVO.setChatRoomName(chatAddVO.getName());
		chatRoomVO.setChatRoomNum(chatNum);
		chatRoomVO.setChatRoomCreator(userVO.getUserId());
		int r = chatDAO.addChat(chatRoomVO);
		chatAddVO.setChatRoomNum(chatNum);
		
		r = chatDAO.addUser(chatAddVO);
		
		if(r > 0) {
			result.put("flg", 1);
			result.put("chatRoomNum", chatNum);
			return result;
		}
		
		result.put("flg", 0);
		
		return result;
				
	}
	
	
	@Transactional
	public ChatRoomVO getChatInfo(ChatRoomVO chatRoomVO,String userId) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("chatRoomNum", chatRoomVO.getChatRoomNum());
		chatRoomVO = chatDAO.getChatInfo(chatRoomVO);
		if(chatRoomVO == null) {
			return null;
		}
		chatRoomVO.setAlarmStatus(chatDAO.getChatAlarm(info));
		
		return chatRoomVO;
	}
	
	@Transactional
	public ChatContentsVO addContents(ChatContentsVO chatContentsVO, UserVO userVO) throws Exception{
		if(chatContentsVO.isSystem()) {
			String name = chatDAO.getUserInfo(chatContentsVO);
			chatContentsVO.setChatContents(name + " 님이 나갔습니다.");
			chatContentsVO.setSender("system");
			chatDAO.addContents(chatContentsVO);
			
			return chatContentsVO;
		}else {
			
			chatContentsVO.setSender(userVO.getUserId());
			chatDAO.addContents(chatContentsVO);
			chatContentsVO = chatDAO.getContentsInfo(chatContentsVO);
			chatContentsVO.setName(chatDAO.getUserInfo(chatContentsVO));
		}
		log.info("sender name : {}", chatContentsVO);
		
		
		return chatContentsVO;
	}
	
	@Transactional
	public List<ChatContentsVO> getChatContentsList(ChatRoomVO chatRoomVO, String userId) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("chatRoomNum", chatRoomVO.getChatRoomNum());
		info.put("userId", userId);
		ChatPersonVO chatPersonVO = chatDAO.getChatPersonDetail(info);
		info.clear();
		info.put("chatRoomNum", chatRoomVO.getChatRoomNum());
		info.put("joinedAt", chatPersonVO.getJoinedAt());
		return chatDAO.getChatContentsList(info);
	}
	
	@Transactional
	public Map<String, Object> fileUpload(MultipartFile file, String chatRoomNum, UserVO userVO) throws Exception{
		ChatContentsVO chatContentsVO = new ChatContentsVO();
		chatContentsVO.setSender(userVO.getUserId());
		chatContentsVO.setChatContents(file.getOriginalFilename());
		chatContentsVO.setChatRoomNum(chatRoomNum);
		//파일 넣은 메세지 저장하기
		int result = chatDAO.addFileContents(chatContentsVO);
		
		chatContentsVO = chatDAO.getChatContentsInfo(chatContentsVO.getChatNum());
		String name = chatDAO.getUserInfo(chatContentsVO);
		chatContentsVO.setName(name);
		
		log.info("chatContenstVO : {}", chatContentsVO);
		
		//파일 하드디스크 저장 후 DB에 파일 이름 저장
		ChatFilesVO chatFilesVO = new ChatFilesVO();
		String fileName = fileManager.fileSave(path.concat("chatFile"), file);
		chatFilesVO.setSaveName(fileName);
		chatFilesVO.setChatNum(chatContentsVO.getChatNum());
		chatFilesVO.setOriginName(file.getOriginalFilename());
		
		chatDAO.addFile(chatFilesVO);
		
		
		//반환
		Map<String, Object> r = new HashMap<>();
		
		r.put("chatContentsVO", chatContentsVO);
		r.put("chatFilesVO", chatFilesVO);
		
		return r;
	
	}

	public FileVO fileDown(NoticeFilesVO filesVO) throws Exception{
		
		return chatDAO.getFileDetail(filesVO);
	}

	public List<ChatPersonVO> getChatUserInfo(String chatRoomNum) throws Exception{
		// TODO Auto-generated method stub
		return chatDAO.getChatUserInfo(chatRoomNum);
	}

	public List<UserVO> getChatUsersDetail(String chatRoomNum) throws Exception{
		
		List<UserVO> list = chatDAO.getChatUsersDetail(chatRoomNum);
//		List<String> result = new ArrayList<>();
//		for(UserVO l : list) {
//			result.add(l.getDeptName() + " " + l.getName() + " " + l.getPosition());
//		}
		
		return list;
	}

	public int updateLastReadAt(String userId, String chatRoomNum) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("chatRoomNum", chatRoomNum);
		return chatDAO.updateLastReadAt(info);
	}

	public int updateAlarm(UserVO userVO, String chatNum) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userVO.getUserId());
		info.put("chatRoomNum", chatNum);
		
		return chatDAO.updateAlarm(info);
		
	}

	public List<ChatContentsVO> getChatMore(String chatRoomNum, String chatNum) throws Exception{
		Map<String, Object> info = new HashMap<>();
		if(chatNum != null) {
			
			info.put("chatRoomNum", chatRoomNum);
			info.put("chatNum", Long.parseLong(chatNum));
		}
		
		List<ChatContentsVO> list = chatDAO.getChatMore(info);
		
//		for(ChatContentsVO l : list) {
//			log.info("getChatMore : {}", l);
//		}
		return list;
	}
	
	public int exit(String chatRoomNum, String userId) throws Exception{
		
		log.info("chatRoomNum : {}", chatRoomNum);
		log.info("userId : {}", userId);
		Map<String, Object> info = new HashMap<>();
		info.put("chatRoomNum", chatRoomNum);
		info.put("userId", userId);
		
		int result = chatDAO.exit(info);
		
		return result;
	}
	@Transactional
	public ChatContentsVO invite(String chatRoomNum, String[] inviteUser) throws Exception{
		String inviteUsers = "";
		ChatPersonVO chatPersonVO = new ChatPersonVO();
		chatPersonVO.setChatRoomNum(chatRoomNum);
		for(String a : inviteUser) {
			chatPersonVO.setUserId(a);
			int result = chatDAO.findExist(chatPersonVO);
			if(result > 0) {
				chatDAO.updateExit(chatPersonVO);
			}else {
				
				chatDAO.invite(chatPersonVO);
			}
			String name = chatDAO.getUserName(a);
			inviteUsers += name + ", ";
		}
		inviteUsers.trim();
		String result = inviteUsers.substring(0, inviteUsers.length() - 2);
		result += "님이 초대되었습니다.";
		ChatContentsVO chatContentsVO = new ChatContentsVO();
		chatContentsVO.setChatContents(result);
		chatContentsVO.setSender("system");
		chatContentsVO.setChatRoomNum(chatRoomNum);
		chatDAO.addContents(chatContentsVO);
		return chatContentsVO;
	}

}
