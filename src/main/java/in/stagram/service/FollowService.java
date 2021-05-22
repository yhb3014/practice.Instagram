package in.stagram.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stagram.domain.Follow;
import in.stagram.repository.FollowRepository;

@Service
public class FollowService {
	
	@Autowired
	private FollowRepository followRepository;
	@Autowired
	private UserService userservice;
	
	public void save(int login_id, int page_id) {
		Follow f = new Follow();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		f.setFollower(userservice.findById(page_id));
		f.setFollowing(userservice.findById(login_id));
		f.setFollow_date(timestamp);
		
		followRepository.save(f);
	}
	
	public void deleteByFollowingIdAndFollowerId(int id1, int id2) {
		followRepository.deleteByFollowingIdAndFollowerId(id2, id1);
	}
	
	public boolean find(int id, String userId) {
		if(followRepository.countByFollowerIdAndFollowingUserId(id, userId)==0) {
			return false;
		}
		return true;
	}
	
	public int countByFollowerId(int id) {
		return followRepository.countByFollowerId(id);
	}
	
	public int countByFollowingId(int id) {
		return followRepository.countByFollowingId(id);
	}
	
	public List<Follow> findByFollowingId(int id){
		return followRepository.findByFollowingId(id);
	}
}
