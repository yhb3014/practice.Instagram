package in.stagram.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stagram.domain.Chat;
import in.stagram.repository.ChatRepository;

@Service
public class ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	@Autowired
	private UserService userService;
	
	public void save(int r, int s, String c) {
		Chat chat = new Chat();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		chat.setReceive(userService.findById(r));
		chat.setSend(userService.findById(s));
		chat.setContent(c);
		chat.setWritetime(timestamp);
		
		chatRepository.save(chat);
	}
	
	public List<Chat> findByReceiveIdAndSendId(int r, int s){
		return chatRepository.findByReceiveIdAndSendId(s, r);
	}
	
	public void deleteUser(int id) {
		chatRepository.deleteByReceiveId(id);
		chatRepository.deleteBySendId(id);
	}
}
