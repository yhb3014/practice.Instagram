package in.stagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.stagram.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	public List<Post> findByUserIdOrderByIdDesc(int id);
	
	public int countByUserId(int id);
}
