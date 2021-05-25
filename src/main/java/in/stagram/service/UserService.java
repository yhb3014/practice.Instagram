package in.stagram.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import in.stagram.domain.User;
import in.stagram.model.UserRegistrationModel;
import in.stagram.repository.UserRepository;
import in.stagram.utils.EncryptionUtils;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User login(String userId, String password) {
		User user = userRepository.findOneByUserId(userId);
		if(user == null)
			return null;
		String pw = EncryptionUtils.encryptMD5(password);
		if(user.getPassword().equals(pw)==false)
			return null;
		return user;
	}
	
	public User findByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}
	
	public User findById(int id) {
		return userRepository.findById(id);
	}
	
	public boolean hasErrors(UserRegistrationModel userModel, BindingResult  bindingResult) {
		if(bindingResult.hasErrors())
			return true;
		
		User user = userRepository.findOneByUserId(userModel.getUserid());
		if(user != null) {
			bindingResult.rejectValue("userid", null, "사용자 아이디가 중복됩니다.");
			return true;
		}
		return false;
	}
	
	public User createEntity(UserRegistrationModel userModel) {
		User user = new User();
		String pw = EncryptionUtils.encryptMD5(userModel.getPasswd1());
		user.setPassword(pw);
		user.setUserId(userModel.getUserid());
		user.setName(userModel.getName());
		user.setPhone(userModel.getPhone());
		user.setEnable(1);
		user.setUserType("user");
		return user;
	}
	
	public void save_u(User user) {
		userRepository.save(user);
	}
	
	public void save(UserRegistrationModel userModel) {
		User user = createEntity(userModel);
		save_u(user);
	}
	
	public void img_update(String userId, String profile_photo) {
		User user = findByUserId(userId);
		user.setProfile_photo(profile_photo);
		
		save_u(user);
	}
	
	
	public void profile_update(String userId, String name, String website, String introduce) {
		User user = findByUserId(userId);
		user.setName(name);
		user.setWebsite(website);
		user.setIntroduce(introduce);
		
		save_u(user);
	}
	
	public List<User> findByUserIdContains(String word){
		return userRepository.findByUserIdContains(word);
	}
	
	public int countByUserIdContains(String word) {
		return userRepository.countByUserIdContains(word);
	}
	
	public void enable_user(int id, int t) {
		User user = findById(id);
		user.setEnable(t);
		
		save_u(user);
	}
	
	public boolean IsSecret(int id) {
		User u = userRepository.findById(id);
		if (u.getEnable() == 1)
			return true;
		return false;
	}
	
	public boolean user_exit2(int uid, String pswd) {
		String pw = EncryptionUtils.encryptMD5(pswd);
		if(userRepository.countByIdAndPassword(uid, pw)==0) {
			return true;
		}
		return false;
	}
	
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}
}
