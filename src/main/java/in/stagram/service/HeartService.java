package in.stagram.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stagram.domain.Heart;
import in.stagram.domain.Post;
import in.stagram.domain.User;
import in.stagram.repository.HeartRepository;

@Service
public class HeartService {
	
	@Autowired
	private HeartRepository heartRepository;
	
	public void save(Post p, User u) {
		Heart h = new Heart();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		h.setPost(p);
		h.setUser(u);
		h.setClicktime(timestamp);
		
		heartRepository.save(h);
	}
	
	public int countByPostId(int id) {
		return heartRepository.countByPostId(id);
	}
	
	public int countByPostIdAndUserId(int pid, int uid) {
		return heartRepository.countByPostIdAndUserId(pid, uid);
	}
	
	public void deleteByPostIdAndUserId(int pid, int uid) {
		heartRepository.deleteByPostIdAndUserId(pid, uid);
	}
	
	public void deleteByPostId(int id) {
		heartRepository.deleteByPostId(id);
	}
	
	public List<Heart> findByPostId(int id){
		return heartRepository.findByPostId(id);
	}
}
