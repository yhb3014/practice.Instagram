package in.stagram.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.stagram.domain.Chat;
import in.stagram.domain.User;
import in.stagram.service.ChatService;
import in.stagram.service.UserService;

@Controller
public class ChatController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ChatService chatService;
	
	@RequestMapping("/main/user/message/{id}")
	public String message(@PathVariable("id") int id, Model model) throws Exception{
		model.addAttribute("page_id", id);
		return "/main/user/message";
	}
	
	@RequestMapping("/chat/list/{id}")
	@ResponseBody
	private List<Chat> chatlist(@PathVariable int id) throws Exception{
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUserId(userId);
		
		List<Chat> chatting = chatService.findByReceiveIdAndSendId(user.getId(), id);
		List<Chat> c = chatService.findByReceiveIdAndSendId(id, user.getId());
		
		for (Chat t : c)
			chatting.add(t);
		
		Chat ccc = new Chat();
		Collections.sort(chatting, ccc);
		return chatting;
	}
	
	@RequestMapping("/chat/insert")
	@ResponseBody
	private int chat_insert(@RequestParam int sendid, @RequestParam int receiveid, @RequestParam String message) throws Exception{
		chatService.save(receiveid, sendid, message);
		return 1;
	}
}
