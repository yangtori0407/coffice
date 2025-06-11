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
	public void chatList(Model model) throws Exception{
		List<ChatRoomVO> list = chatService.getList();
		model.addAttribute("list", list);
		
	}
	
	@GetMapping("addChat")
	public void addChat() throws Exception{
		
	}
	
	//json 받음
	@PostMapping("addChat")
	@ResponseBody
	public Map<String, Object> addChat(@RequestBody ChatAddVO chatAddVO, Model model, Authentication authentication) throws Exception{
		//동일한 사람과 톡방을 만들었을 때 어떻게 중복 처리를 해야하지...?
		UserVO userVO = new UserVO(); //(UserVO)authentication.getPrincipal();
		Map<String, Object> result = chatService.addChat(chatAddVO, userVO);
		
		return result;
	}

}
