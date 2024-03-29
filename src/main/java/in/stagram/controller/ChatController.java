package in.stagram.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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
	public List<Chat> chatlist(@PathVariable int id) throws Exception{
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
	public int chat_insert(@RequestParam int sendid, @RequestParam int receiveid, @RequestParam String message) throws Exception{
		chatService.save(receiveid, sendid, message);
		return 1;
	}
	
	@RequestMapping("/sendchat")
	public String message(HttpServletRequest request, Model model) throws Exception{
		String s = request.getParameter("sendid");
		String r = request.getParameter("receiveid");
		String chat = request.getParameter("message");
		
		int sendid = Integer.parseInt(s);
		int receiveid = Integer.parseInt(r);
		
		chatService.save(receiveid, sendid, chat);
		
		String redirect_url = "redirect:/main/user/message/" + receiveid;
		return redirect_url;
	}
	
	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public Chat sendMessage(@Payload Chat chat) {
		return chat;
	}

	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public Chat addUser(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chat.getSend().getName());
		return chat;
	}
}
