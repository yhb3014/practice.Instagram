package in.stagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import in.stagram.model.User;
import in.stagram.model.UserRegistrationModel;
import in.stagram.repository.UserRepository;
import in.stagram.utils.EncryptionUtils;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User login(String userId, String password) {
		User user = userRepository.findOneByUserId(userId);
		if(user == null)
			return null;
		String pw = EncryptionUtils.encryptMD5(password);
		if(user.getPassword().equals(pw)==false)
			return null;
		return user;
	}
	
	@Transactional
	public User findByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}
	
	@Transactional
	public User findById(int id) {
		return userRepository.findById(id);
	}
	
	@Transactional
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
	
	@Transactional
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
	
	@Transactional
	public void save(UserRegistrationModel userModel) {
		User user = createEntity(userModel);
		userRepository.save(user);
	}
}
