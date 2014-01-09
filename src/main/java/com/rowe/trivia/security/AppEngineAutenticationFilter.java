package com.rowe.trivia.security;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.rowe.trivia.domain.User;

public class AppEngineAutenticationFilter extends AbstractPreAuthenticatedProcessingFilter{

	public AppEngineAutenticationFilter() {
		setAuthenticationManager(new AppEngineAuthenticationManager());
	}
	
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		if(isAppEngineRequest(request)){
			return "system";
		}
		return null;
	}

	private boolean isAppEngineRequest(HttpServletRequest request) {
		if(request.getHeader("X-AppEngine-QueueName") != null){
			return true;
		}
		return false;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "N/A";
	}

	private static class AppEngineAuthenticationManager implements AuthenticationManager{

		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			return new PreAuthenticatedAuthenticationToken(new User(), "N/A", 
				Arrays.asList(
					new SimpleGrantedAuthority("ROLE_USER"), 
					new SimpleGrantedAuthority("ROLE_ADMIN")
				));
		}
		
	}
}
