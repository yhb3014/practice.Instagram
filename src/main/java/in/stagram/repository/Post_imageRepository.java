package in.stagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import in.stagram.domain.Post_image;

public interface Post_imageRepository extends JpaRepository<Post_image, Integer>{
	public List<Post_image> findBypostId(int id);
	
	@Query(nativeQuery = true, value = "select * from Post_image p GROUP BY postid")
	public List<Post_image> findByGroupbyPostId();
	
	@Modifying
	@Transactional
	public void deleteByPostId(int id);
}
