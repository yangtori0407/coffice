package com.coffice.app.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("send")
	public void send(Model model) throws Exception{
		model.addAttribute("message", "send");
	}
	@GetMapping("receive")
	public void receive(Model model) throws Exception{
		model.addAttribute("message", "receive");
	}
	
	@GetMapping("add")
	public void add() throws Exception{
		
	}
	
	@PostMapping("add")
	public String add(MessageVO messageVO,@RequestParam("receivers") String[] receivers, String replyEmail, Authentication authentication) throws Exception{
		log.info("messageVO : {}", messageVO);
		log.info("replyEmail : {}", replyEmail);
		for(String s : receivers) {
			log.info("receiver: {}", s);
		}
		messageService.sendMessage(messageVO, receivers, replyEmail, authentication.getName());
		
		return "redirect:./main";
	}
}
