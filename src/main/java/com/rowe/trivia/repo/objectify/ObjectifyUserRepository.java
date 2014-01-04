package com.rowe.trivia.repo.objectify;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;

import com.googlecode.objectify.ObjectifyService;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.repo.UserRepository;

public class ObjectifyUserRepository extends ObjectifyRepositorySupport<User> implements UserRepository{

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		return loadUserByUsername(userId);
	}
	

	@Override
	public User loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user =  ObjectifyService.ofy().load().type(User.class).id(username).now();
		if(user == null){
			throw new UsernameNotFoundException("User not found: " + user);
		}
		return user;
	}


	@Override
	protected Class<User> getEntityType() {
		return User.class;
	}

}
