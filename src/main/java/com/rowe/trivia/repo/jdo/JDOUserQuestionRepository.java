package com.rowe.trivia.repo.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.repo.UserQuestionRepository;
import com.rowe.trivia.repo.UserRepository;

public class JDOUserQuestionRepository implements UserQuestionRepository{
	private static final Logger logger = LoggerFactory.getLogger(JDOUserQuestionRepository.class);
	
	private final PersistenceManagerFactory persistenceManagerFactory;
	private final UserRepository userRepo;
	
	public JDOUserQuestionRepository(PersistenceManagerFactory persistenceManagerFactory, UserRepository userRepo) {
		this.persistenceManagerFactory = persistenceManagerFactory;
		this.userRepo = userRepo;
	}
	
	@Override
	public void save(UserQuestion obj) {
		logger .info("Saving {}", obj);
		persistenceManagerFactory.getPersistenceManager()
			.makePersistent(obj);
	}

	@Override
	public List<UserQuestion> listAll() {
		PersistenceManager manager = persistenceManagerFactory.getPersistenceManager();
		Query query = manager.newQuery(UserQuestion.class);
		return (List<UserQuestion>) query.execute();
	}

	@Override
	public UserQuestion findByUsernameContest(String username, String contestId) {
		User user = userRepo.loadUserByUsername(username);
		
		PersistenceManager manager = persistenceManagerFactory.getPersistenceManager();
		Query query = manager.newQuery(UserQuestion.class);
		query.setFilter("user == :user and contestId == :contestId");
		List<UserQuestion> results = (List<UserQuestion>)query.execute(user, contestId);
		if(results.isEmpty()){
			return null;
		}else if(results.size() > 1){
			throw new IllegalArgumentException("more than one result returned for " + username + " " + contestId + " " + results.size());
		}else {
			return results.get(0);
		}
	}

	@Override
	public Iterable<UserQuestion> findByUser(User user) {
		PersistenceManager manager = persistenceManagerFactory.getPersistenceManager();
		Query query = manager.newQuery(UserQuestion.class);
		query.setFilter("user == :user");
		return (List<UserQuestion>)query.execute(user);
	}

}
