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
		pi.setTag(p.getTag());
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
	
	public void deleteById(int id) {
		postRepository.deleteById(id);
	}
	
	public List<Post> findByUserId(int id){
		return postRepository.findByUserId(id);
	}
	
	public List<Post> findByUserUserId(String userId){
		return postRepository.findByUserUserId(userId);
	}
	
	public List<Post> findByPostlimit9(){
		return postRepository.findByPostlimit9();
	}
	
	public int countByTagContains(String word) {
		return postRepository.countByTagContains(word);
	}
	
	public List<Post> findByTagContains(String word){
		return postRepository.findByTagContains(word);
	}
}
