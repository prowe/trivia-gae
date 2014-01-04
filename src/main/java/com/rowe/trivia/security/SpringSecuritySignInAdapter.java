package com.rowe.trivia.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.web.context.request.NativeWebRequest;

import com.rowe.trivia.domain.User;

public class SpringSecuritySignInAdapter implements SignInAdapter{
	private static Logger logger = LoggerFactory.getLogger(SpringSecuritySignInAdapter.class);
	
	@Autowired
	private SocialUserDetailsService socialUserDetailsService;
	
	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		User user = (User) socialUserDetailsService.loadUserByUserId(userId);
		user.signInAsCurrentUser();
		logger.info("Signing in user {} using connection {}", userId, connection);
		//SecurityContextHolder.getContext().setAuthentication(
		//	new PreAuthenticatedAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("USER")))
			//new SocialAuthenticationToken(connection, user, null, Arrays.asList(new SimpleGrantedAuthority("USER")))
			//new UsernamePasswordAuthenticationToken(userId, null, null)
		//);
		return null;
	}

}
