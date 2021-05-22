package in.stagram.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import in.stagram.service.MyAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyAuthenticationProvider myAuthenticationProvider;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/res/**");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/admin/**").hasAuthority("ROLE_ADMIB")
				.antMatchers("/guest/**")
				.permitAll()
				.antMatchers("/")
				.permitAll()
				.antMatchers("/", "/images/**", "/resources/**", "/resources/images/**") 
				.permitAll()
				.antMatchers("/**")
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/guest/login")
				.loginProcessingUrl("/loginProc")
				.failureUrl("/guest/login?error")
				.defaultSuccessUrl("/main", true)
				.usernameParameter("loginId")
				.passwordParameter("passwd")
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/guest/logoutProc"))
				.logoutSuccessUrl("/guest/logout")
				.invalidateHttpSession(true)
			.and()
				.authenticationProvider(myAuthenticationProvider);
	}
}