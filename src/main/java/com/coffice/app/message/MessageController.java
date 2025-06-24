package com.coffice.app.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/message/")
@Slf4j
public class MessageController {
	
	@ModelAttribute("message")
	public String kind() {
		return "message";
	}
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping("main")
	public void main() throws Exception{
		
	}
	
	@GetMapping("add")
	public void add() throws Exception{
		
	}
	
	@PostMapping("add")
	public String add(MessageVO messageVO, int kind, Authentication authentication) throws Exception{
		log.info("messageVO : {}", messageVO);
		messageService.sendMessage(messageVO, 2, authentication.getName());
		
		return "redirect:./main";
	}
}
