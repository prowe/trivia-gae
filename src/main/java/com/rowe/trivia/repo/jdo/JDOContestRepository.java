package com.rowe.trivia.repo.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.UserRepository;

public class JDOContestRepository implements ContestRepository{
	private static final Logger logger = LoggerFactory.getLogger(JDOContestRepository.class);
	
	private final PersistenceManagerFactory persistenceManagerFactory;
	private final UserRepository userRepo;
	
	public JDOContestRepository(PersistenceManagerFactory persistenceManagerFactory, UserRepository userRepo) {
		this.persistenceManagerFactory = persistenceManagerFactory;
		this.userRepo = userRepo;
	}

	@Override
	public void save(Contest obj) {
		logger .info("Saving {}", obj);
		persistenceManagerFactory.getPersistenceManager()
			.makePersistent(obj);
	}

	@Override
	public List<Contest> listAll() {
		PersistenceManager manager = persistenceManagerFactory.getPersistenceManager();
		Query query = manager.newQuery(Contest.class);
		return (List<Contest>) query.execute();
	}
	
	@Override
	public Contest getContest(String userName, String contestId) {
		User user = userRepo.loadUserByUsername(userName);
		
		PersistenceManager manager = persistenceManagerFactory.getPersistenceManager();
		Query query = manager.newQuery(Contest.class);
		query.setFilter("sponsor == :sponsor and contestId == :contestId");
		return (Contest)query.execute(user, contestId);
	}
}
