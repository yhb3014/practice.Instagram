package in.stagram.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stagram.domain.Comment;
import in.stagram.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	
	public void save(int pid, int uid, String s) {
		Comment c = new Comment();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		c.setContent(s);
		c.setPost(postService.findById(pid));
		c.setUser(userService.findById(uid));
		c.setWritetime(timestamp);
		
		commentRepository.save(c);
	}
	
	
	public List<Comment> findByPostId(int pid){
		return commentRepository.findByPostId(pid);
	}
	
	public int deleteById(int id) {
		commentRepository.deleteById(id);
		return 1;
	}
	
	public int countByPostId(int id) {
		return commentRepository.countByPostId(id);
	}
	
	public void deleteByPostId(int id) {
		commentRepository.deleteByPostId(id);
	}
	
	public void deleteByUserId(int id) {
		commentRepository.deleteByUserId(id);
	}
}
