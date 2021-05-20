package in.stagram.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import in.stagram.model.User;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginId = authentication.getName();
		String passwd = authentication.getCredentials().toString();
		return authenticate(loginId, passwd);
	}
	
	private Authentication authenticate(String loginId, String passwd) {
		User user = userService.login(loginId, passwd);
		if (user == null) return null;
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		String role = "";
		switch (user.getUserType()) {
		case "admin":  role = "ROLE_ADMIN"; break;
		case "user":  role = "ROLE_USER"; break;
		}
		grantedAuthorities.add(new SimpleGrantedAuthority(role));
		return new MyAutiontication(loginId, passwd, grantedAuthorities, user);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	public class MyAutiontication extends UsernamePasswordAuthenticationToken{
		
		private static final long serialVersionUID = 1L;
		User user;
		
		public MyAutiontication (String loginId, String passwd, List<GrantedAuthority> grantedAuthority, User user) {
			super(loginId, passwd, grantedAuthority);
			this.user=user;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
	}
}
