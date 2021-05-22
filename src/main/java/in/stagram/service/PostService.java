package in.stagram.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stagram.domain.Post;
import in.stagram.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public int save(Post p) {
		Post pi = new Post();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		pi.setUser(p.getUser());
		pi.setLocation(p.getLocation());
		pi.setDescription(p.getDescription());
		pi.setCreate_date(timestamp);
		
		postRepository.save(pi);
		return pi.getId();
	}
	
	public void flush() {
		postRepository.flush();
	}
	
	public List<Post> findByUserIdOrderByIdDesc(int id){
		return postRepository.findByUserIdOrderByIdDesc(id);
	}
	
	public int countByUserId(int id) {
		return postRepository.countByUserId(id);
	}
	
	public Post findById(int id) {
		return postRepository.findById(id);
	}
}
