package in.stagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import in.stagram.domain.Heart;

public interface HeartRepository extends JpaRepository<Heart, Integer> {
	public int countByPostId(int id);
	public int countByPostIdAndUserId(int pid, int uid);
	
	@Modifying
	@Transactional
	public void deleteByPostIdAndUserId(int pid, int uid);
	
	@Modifying
	@Transactional
	public void deleteByPostId(int id);
	
	List<Heart> findByPostId(int id);
}
