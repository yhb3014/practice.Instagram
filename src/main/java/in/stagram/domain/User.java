package in.stagram.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String userId;
	private String password;
	private String name;
	private String introduce;
	private String phone;
	private String profile_photo;
	private String website;
	private String userType;
	private int enable;
	
}
