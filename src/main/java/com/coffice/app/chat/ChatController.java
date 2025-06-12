package com.coffice.app.chat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.chat.vo.ChatAddVO;
import com.coffice.app.chat.vo.ChatRoomVO;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/chat/")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	private final SimpMessagingTemplate template;
	
	@Autowired
	public ChatController(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	@GetMapping("main")
	public void chatList(Model model, Authentication authentication) throws Exception{
		UserVO userVO = new UserVO(); //(UserVO)authentication.getPrincipal();
		List<ChatRoomVO> list = chatService.getList(userVO);
		model.addAttribute("list", list);
		
	}
	
	@GetMapping("addChat")
	public void addChat() throws Exception{
		
	}
	
	//json 받음
	@PostMapping("addChat")
	@ResponseBody
	public Map<String, Object> addChat(@RequestBody ChatAddVO chatAddVO, Model model, Authentication authentication) throws Exception{
		
		UserVO userVO = new UserVO(); //(UserVO)authentication.getPrincipal();
		Map<String, Object> result = chatService.addChat(chatAddVO, userVO);
		
		return result;
	}
	
	@GetMapping("chatRoom")
	public void chatRoom(ChatRoomVO chatRoomVO, Model model) throws Exception{
		String chatName = chatService.getChatName(chatRoomVO);
		model.addAttribute("chatName", chatName);
		
	}

}
