package com.rowe.trivia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.rowe.trivia.repo.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
			"/_ah/**", //app engine debug stuff
			"/resources/**", 
			"/debug/**"
		);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage("/");

		http.logout()
			.logoutSuccessUrl("/");
		
		
		//Anyone can access the urls
		http.authorizeRequests().antMatchers(
			"/signin/**",
			"/auth/**",
			"/users/signup*",
			"/"
		).permitAll();
		
		//The rest of the our application is protected.
		http.authorizeRequests().antMatchers(
			"/**"
		).hasRole("USER");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userRepo);
    }
}
