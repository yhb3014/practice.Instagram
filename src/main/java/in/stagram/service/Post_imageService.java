package in.stagram.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stagram.domain.Post_image;
import in.stagram.repository.Post_imageRepository;

@Service
public class Post_imageService {
	
	@Autowired
	private Post_imageRepository post_imageRepository;
	
	public void save(Post_image pi) {
		Post_image p = new Post_image();
		
		p.setPostId(pi.getPostId());
		p.setFilename(pi.getFilename());
		p.setFileOriname(pi.getFileOriname());
		
		post_imageRepository.save(p);
	}
	
	public List<Post_image> findAll(){
		return post_imageRepository.findAll();
	}
	
	public List<Post_image> findByGroupbyPostId() {
		return post_imageRepository.findByGroupbyPostId();
	}
}
