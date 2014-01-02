package com.rowe.trivia.repo.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;

import com.rowe.trivia.domain.User;
import com.rowe.trivia.repo.UserRepository;

public class JDOUserRepository implements UserRepository{
	private static Logger logger = LoggerFactory.getLogger(JDOUserRepository.class);
	
	private final PersistenceManagerFactory persistenceManagerFactory;
	
	public JDOUserRepository(PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId)
			throws UsernameNotFoundException, DataAccessException {
		return loadUserByUsername(userId);
	}

	@Override
	public void save(User obj) {
		logger.info("Saving {}", obj);
		persistenceManagerFactory.getPersistenceManager()
			.makePersistent(obj);
	}

	@Override
	public List<User> listAll() {
		PersistenceManager manager = persistenceManagerFactory.getPersistenceManager();
		Query query = manager.newQuery(User.class);
		return (List<User>) query.execute();
	}

	@Override
	public User loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.info("Loading user for name {}", username);
		PersistenceManager manager = persistenceManagerFactory.getPersistenceManager();
		Query query = manager.newQuery(User.class);
		query.setFilter("username == :userName");
		List<User> results = (List<User>) query.execute(username);
		if(results.isEmpty()){
			throw new UsernameNotFoundException("User not found: " + username);
		}else if(results.size() > 1){
			throw new IllegalArgumentException("more than one result returned for " + username + results.size());
		}else {
			return results.get(0);
		}
	}

}
