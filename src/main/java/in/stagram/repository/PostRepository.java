package in.stagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import in.stagram.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	public List<Post> findByUserIdOrderByIdDesc(int id);
	
	public int countByUserId(int id);
	
	public Post findById(int id);
	
	List<Post> findByUserUserId(String userId);
	
	@Query(nativeQuery = true, value = "select * from Post p JOIN user u on u.id = p.userid WHERE u.enable = 1 group by p.id ORDER BY p.id desc Limit 9;")
	List<Post> findByPostlimit9();
	
	@Modifying
	@Transactional
	void deleteById(int id);
	
	List<Post> findByUserId(int id);
}
