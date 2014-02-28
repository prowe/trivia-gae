package com.rowe.trivia;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.j2ee.J2eePreAuthenticatedProcessingFilter;

import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.security.AppEngineAutenticationFilter;

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
		http.csrf().disable();
		
		http.formLogin()
			.loginPage("/");

		http.logout()
			.logoutSuccessUrl("/");
		
		
		//Anyone can access the urls
		http.authorizeRequests().antMatchers(
			"/signin/**",
			"/auth/**",
			"/users/signup*",
			"/",
			"/mapreduce/*", //TODO: remove and move to auth manager
			"/_ah/pipeline/*" //TODO: remove and move to auth manager
		).permitAll();
		
		//The rest of the our application is protected.
		http.authorizeRequests().antMatchers(
			"/**"
		).hasRole("USER");
		
		http.addFilter(containerAuthorizedFilter());
	}
	
	@Bean
	public Filter containerAuthorizedFilter() throws Exception {
		AppEngineAutenticationFilter filter = new AppEngineAutenticationFilter();
		filter.setContinueFilterChainOnUnsuccessfulAuthentication(true);
		//filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userRepo);
    }
}
