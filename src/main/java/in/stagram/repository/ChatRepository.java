package in.stagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import in.stagram.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer>{
	public List<Chat> findByReceiveIdAndSendId(int s, int r);
	
	@Modifying
	@Transactional
	public void deleteBySendId(int id);

	@Modifying
	@Transactional
	public void deleteByReceiveId(int id);
	
}
