package com.coffice.app.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.chat.vo.ChatAddVO;
import com.coffice.app.chat.vo.ChatContentsVO;
import com.coffice.app.chat.vo.ChatFilesVO;
import com.coffice.app.chat.vo.ChatPersonVO;
import com.coffice.app.chat.vo.ChatRoomVO;
import com.coffice.app.files.FileVO;
import com.coffice.app.posts.notice.NoticeFilesVO;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/chat/")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@ModelAttribute("chat")
	public String kind() {
		return "chat";
	}
	
	private final SimpMessagingTemplate template;
	
	@Autowired
	public ChatController(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	//메세지 보내는 부분
	@MessageMapping("/sendMessage")
	public void sendMessage(ChatContentsVO chatContentsVO, Authentication authentication) throws Exception{
		UserVO userVO = (UserVO)authentication.getPrincipal();
		//userVO.setUserId("test1"); //로그인 연결시 없애기
		
		if(!chatContentsVO.isFlag()) {
			
			chatContentsVO = chatService.addContents(chatContentsVO, userVO);
		}
		
		log.info("보낸 것 chatContentsVO : {}", chatContentsVO);
		template.convertAndSend("/sub/chatRoom." + chatContentsVO.getChatRoomNum(), chatContentsVO);
		log.info("/sub/chatRoom." + chatContentsVO.getChatRoomNum());
		
		//채팅 알람
		List<ChatPersonVO> users = chatService.getChatUserInfo(chatContentsVO.getChatRoomNum());
		ChatRoomVO chatRoomVO = new ChatRoomVO();
		chatRoomVO.setChatRoomNum(chatContentsVO.getChatRoomNum());
		chatRoomVO = chatService.getChatInfo(chatRoomVO,userVO.getUserId());
		Map<String, Object> alert = new HashMap<>();
		alert.put("chatRoomName", chatRoomVO.getChatRoomName());
		alert.put("chatContentsVO", chatContentsVO);
		for(ChatPersonVO u : users) {
//			log.info("chat participate users : {}", u);
			//String 비교는 반드시 equals!!!!!
			if(!u.getUserId().equals(chatContentsVO.getSender()) && u.getAlarmStatus() == 1) {
				log.info("chat participate users : {}", u);
				template.convertAndSend("/sub/chat/user." + u.getUserId(), alert);
			}
		}
	}
	
	@GetMapping("main")
	public void chatList(Model model, Authentication authentication) throws Exception{
		UserVO userVO = (UserVO)authentication.getPrincipal();
		List<ChatRoomVO> list = chatService.getList(userVO);
		log.info("ChatList size : {}", list.size());
		model.addAttribute("list", list);
		model.addAttribute("kind", "메신저");
	}
	
	@GetMapping("addChat")
	public void addChat(Model model) throws Exception{
		model.addAttribute("kind", "메신저 > 방만들기");
	}
	
	//json 받음
	@PostMapping("addChat")
	@ResponseBody
	public Map<String, Object> addChat(@RequestBody ChatAddVO chatAddVO, Model model, Authentication authentication) throws Exception{
		
		UserVO userVO =  (UserVO)authentication.getPrincipal();
		Map<String, Object> result = chatService.addChat(chatAddVO, userVO);
		
		return result;
		
	}
	
	@GetMapping("chatRoom")
	public void chatRoom(ChatRoomVO chatRoomVO, Model model, Authentication authentication) throws Exception{
		String userId = authentication.getName();
		chatRoomVO = chatService.getChatInfo(chatRoomVO, userId);
		List<ChatContentsVO> contents = chatService.getChatContentsList(chatRoomVO);
		List<UserVO> users = chatService.getChatUsersDetail(chatRoomVO.getChatRoomNum());
		chatService.updateLastReadAt(userId, chatRoomVO);
//		for(ChatContentsVO c : contents) {
//			log.info("챗 내용 : {}", c);
//		}
		log.info("chatRoomVO : {}", chatRoomVO);
		model.addAttribute("chatRoomVO", chatRoomVO);
		model.addAttribute("userId", userId);
		model.addAttribute("contents", contents);
		model.addAttribute("users", users);
		model.addAttribute("kind", "메신저");
		
	}
	
	@PostMapping("fileUpload")
	@ResponseBody
	public Map<String, Object> fileUpload(@RequestParam("file") MultipartFile file, String chatRoomNum, Authentication authentication) throws Exception{
		UserVO userVO = (UserVO)authentication.getPrincipal();
		Map<String, Object> result = chatService.fileUpload(file,chatRoomNum, userVO);
		
		return result;
	}
	
	@GetMapping("fileDown")
	public String fileDown(NoticeFilesVO filesVO,Model model) throws Exception{
		FileVO fileVO = (FileVO)chatService.fileDown(filesVO);
		
		model.addAttribute("fileVO", fileVO);
		model.addAttribute("kind", "chatFile");
		
		return "fileDownView";
	}
	
	@PostMapping("updateAlarm")
	public String updateAlarm(Authentication authentication, String chatNum, Model model) throws Exception{
		UserVO userVO = (UserVO)authentication.getPrincipal();
		int result = chatService.updateAlarm(userVO, chatNum);
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	
	@PostMapping("getChatMore")
	@ResponseBody
	public List<ChatContentsVO> getChatMore(String chatRoomNum, String chatNum) throws Exception{
		return chatService.getChatMore(chatRoomNum, chatNum);
	}

}
