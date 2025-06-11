package com.coffice.app.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("addRoom")
	public void addRoom() throws Exception{
		
	}

}
