package com.coffice.app.message;

import java.util.List;

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
	public void send(Model model, Authentication authentication) throws Exception{
		model.addAttribute("kind", "이메일 > 보낸 이메일");
		List<MessageVO> list = messageService.getSendMessage(authentication.getName());
		model.addAttribute("message", "send");
		model.addAttribute("list", list);
	}
	@GetMapping("receive")
	public void receive(Model model, Authentication authentication) throws Exception{
		model.addAttribute("kind", "이메일 > 받은 이메일");
		List<MessageVO> list = messageService.getReceiveMessage(authentication.getName());
		model.addAttribute("message", "receive");
		model.addAttribute("list", list);
	}
	
	@GetMapping("add")
	public void add(Model model) throws Exception{
		model.addAttribute("kind", "이메일 > 작성하기");
	}
	
	@PostMapping("add")
	public String add(MessageVO messageVO,@RequestParam("receivers") String[] receivers,  Authentication authentication, Model model) throws Exception{
		log.info("messageVO : {}", messageVO);
		for(String s : receivers) {
			log.info("receiver: {}", s);
		}
		int result = messageService.sendMessage(messageVO, receivers, authentication.getName());
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	
	@GetMapping("send/detail")
	public String sendDetail(MessageVO messageVO, Model model) throws Exception{
		messageVO = messageService.detail(messageVO);
		model.addAttribute("detail", messageVO);
		model.addAttribute("message", "send");
		model.addAttribute("kind", "이메일 > 보낸 이메일");
		return "message/detail";
	}
	
	@GetMapping("receive/detail")
	public String receiveDetail(MessageVO messageVO, Model model) throws Exception{
		//log.info("도착");
		messageVO = messageService.detail(messageVO);
		model.addAttribute("detail", messageVO);
		model.addAttribute("message", "receive");
		model.addAttribute("kind", "이메일 > 받은 이메일");
		return "message/detail";
	}
	
	@PostMapping("receive/delete")
	public String receiveDelete(Long messageNum, Authentication authentication, Model model) throws Exception{
		//log.info("messageNum : {}", messageNum);
		int result = messageService.receiveDelete(messageNum, authentication.getName());
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	
	@PostMapping("send/delete")
	public String senderDelete(Long messageNum, Authentication authentication, Model model) throws Exception{
		log.info("messageNum : {}", messageNum);
		int result = messageService.sendDelete(messageNum, authentication.getName());
		model.addAttribute("result", result);
		return "commons/ajaxResult";
	}
	
	
	@GetMapping("reply")
	public String reply(Long messageNum, Authentication authentication, Model model) throws Exception{
		MessageVO messageVO = messageService.reply(messageNum, authentication.getName());
		model.addAttribute("detail", messageVO);
		
		return "message/reply";
	}

}
