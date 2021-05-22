package in.stagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.stagram.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findById(int id);
	public User findOneByUserId(String userId);
	public User findByUserId(String userId);
}
