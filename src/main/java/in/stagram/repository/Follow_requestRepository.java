package in.stagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import in.stagram.domain.Follow_request;

public interface Follow_requestRepository extends JpaRepository<Follow_request, Integer>{
	
	List<Follow_request> findByReceiveId(int id);
	public int countByReceiveId(int id);
	public int countByRequestIdAndReceiveId(int id1, int id2);
	
	
	@Modifying
	@Transactional
	public void deleteByRequestIdAndReceiveId(int id1, int id2);

	@Modifying
	@Transactional
	public void deleteByReceiveId(int id);
	
	@Modifying
	@Transactional
	public void deleteByRequestId(int id);
}
