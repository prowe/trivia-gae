package com.rowe.trivia.repo;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetailsService;

import com.rowe.trivia.domain.User;

public interface UserRepository extends UserDetailsService, SocialUserDetailsService, BaseRepository<User>{

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException;
}
