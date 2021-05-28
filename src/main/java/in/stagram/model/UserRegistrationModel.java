package in.stagram.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRegistrationModel {
	
	@NotNull(message = "ID를 입력하세요.")
	@Size(min = 2,max = 12)
	String userid;
	
	@NotNull(message = "Password를 입력하세요.")
	@Size(min = 8, max = 20, message = "8자리 이상 20자리 이하이어야 합니다.")
	String passwd1;
	
	@NotNull(message = "이름을 입력하세요.")
	String name;
	
	@NotNull(message = "핸드폰 번호를 입력하세요.")
	String phone;
}
