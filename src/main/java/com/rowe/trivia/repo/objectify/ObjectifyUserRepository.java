package com.rowe.trivia.repo.objectify;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
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
	public boolean isUsernameAvailable(String username) {
		//TODO: make this more efficent
		return null == loadUserByUsername(username);
	}

	@Override
	public void saveNewUser(final User user) {
		ObjectifyService.ofy().transact(new Work<Object>() {
			@Override
			public Object run() {
				String username = user.getUsername();
				User existingUser =  ObjectifyService.ofy().load().type(User.class).id(username).now();
				if(existingUser != null){
					//user exists
					throw new UsernameTakenExeption(username);
				}
				ObjectifyService.ofy().save().entity(user);
				return null;
			}
		});
	}

	@Override
	protected Class<User> getEntityType() {
		return User.class;
	}

}
