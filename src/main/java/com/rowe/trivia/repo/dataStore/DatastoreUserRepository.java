package com.rowe.trivia.repo.dataStore;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.repo.UserRepository;

public class DatastoreUserRepository extends DatastoreRepositorySupport<User> implements UserRepository{

	/*
	 * Note: to lock a possible non existant row. First issue a put with no data.
	 * Now the row is known to exist
	 * start a transaction
	 * get the row to lock it
	 * put the row to update it.
	 * commit tx
	 */
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Key key = KeyFactory.createKey(getKind(), username);
		try {
			Entity entity = datastoreService.get(key);
			return fromEntity(entity);
		} catch (EntityNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
	}
	
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		return loadUserByUsername(userId);
	}
	
	@Override
	protected Class<User> getEntityType() {
		return User.class;
	}

	@Override
	protected User fromEntity(Entity entity) {
		//TODO: figure out the mapping for this
		User user = new User();
		user.setUsername(entity.getKey().getName());
		return user;
	}
	@Override
	protected Entity toEntity(User user) {
		Entity entity = new Entity(getKind(), user.getUsername());
		//TODO:
		return entity;
	}
}
