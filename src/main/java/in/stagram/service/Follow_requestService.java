package in.stagram.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stagram.domain.Follow_request;
import in.stagram.domain.User;
import in.stagram.repository.Follow_requestRepository;

@Service
public class Follow_requestService {
	
	@Autowired
	private Follow_requestRepository follow_requestRepository;
	@Autowired
	private UserService userService;
	
	public int request_save(int requestid, int receiveid) {
		Follow_request fr = new Follow_request();
		User requestuser = userService.findById(requestid);
		User receiveuser = userService.findById(receiveid);
		
		fr.setRequest(requestuser);
		fr.setReceive(receiveuser);
		
		follow_requestRepository.save(fr);
		return 1;
	}
	
	public boolean request(int requestid, int receiveid) {
		if(follow_requestRepository.countByRequestIdAndReceiveId(requestid, receiveid) == 1) {
			return true;
		}
		return false;
	}
	
	public int countByReceiveId(int id) {
		return follow_requestRepository.countByReceiveId(id);
	}
	
	public List<Follow_request> findByReceiveId(int id){
		return follow_requestRepository.findByReceiveId(id);
	}
	
	public void deleteByRequestIdAndReceiveId(int id1, int id2) {
		follow_requestRepository.deleteByRequestIdAndReceiveId(id1, id2);
	}
	
	public void delete_user(int id) {
		follow_requestRepository.deleteByReceiveId(id);
		follow_requestRepository.deleteByRequestId(id);
	}
}
