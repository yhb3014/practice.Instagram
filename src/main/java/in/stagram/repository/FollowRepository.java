package in.stagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import in.stagram.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow, Integer>{
	public int countByFollowerIdAndFollowingUserId(int id, String userId);
	
	@Modifying
	@Transactional
	public void deleteByFollowingIdAndFollowerId(int id1, int id2); // 언팔
	
	public int countByFollowerId(int id);
	public int countByFollowingId(int id);
	
	List<Follow> findByFollowingId(int id);
}
