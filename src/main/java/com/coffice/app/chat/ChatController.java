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
	}
	
	@GetMapping("main")
	public void chatList(Model model, Authentication authentication) throws Exception{
		UserVO userVO = (UserVO)authentication.getPrincipal();
		List<ChatRoomVO> list = chatService.getList(userVO);
		log.info("ChatList size : {}", list.size());
		model.addAttribute("list", list);
		
	}
	
	@GetMapping("addChat")
	public void addChat() throws Exception{
		
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
		chatRoomVO = chatService.getChatInfo(chatRoomVO);
		List<ChatContentsVO> contents = chatService.getChatContentsList(chatRoomVO);
		
//		for(ChatContentsVO c : contents) {
//			log.info("챗 내용 : {}", c);
//		}
		log.info("chatRoomVO : {}", chatRoomVO);
		model.addAttribute("chatRoomVO", chatRoomVO);
		model.addAttribute("userId", userId);
		model.addAttribute("contents", contents);
		
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

}
