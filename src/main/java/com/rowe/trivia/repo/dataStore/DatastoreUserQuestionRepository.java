package com.rowe.trivia.repo.dataStore;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.UserQuestionRepository;
import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.repo.dataStore.DatastoreRepositorySupport.ConvertingIterable;

public abstract class DatastoreUserQuestionRepository extends DatastoreRepositorySupport<UserQuestion> implements UserQuestionRepository{
	
	@Autowired
	private ContestRepository contestRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	protected Class<UserQuestion> getEntityType() {
		return UserQuestion.class;
	}

	@Override
	public UserQuestion findByUsernameContest(String username, String contestId) {
		Key contestantKey = KeyFactory.createKey(User.class.getSimpleName(), username);
		Key key = KeyFactory.createKey(contestantKey, getKind(), contestId);
		try {
			Entity entity = datastoreService.get(key);
			return fromEntity(entity);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	@Override
	public Iterable<UserQuestion> findByUser(User user) {
		if(user == null){
			return new ArrayList<UserQuestion>();
		}
		Key contestantKey = KeyFactory.createKey(User.class.getSimpleName(), user.getUsername());
		Query query = new Query(getKind());
		query.setAncestor(contestantKey);
		final Iterable<Entity> it = datastoreService.prepare(query).asIterable();
		return new ConvertingIterable(it);
	}
	
	@Override
	protected Entity toEntity(UserQuestion obj) {
		Key contestantKey = KeyFactory.createKey(User.class.getSimpleName(), obj.getContestant().getUsername());
		Key key = KeyFactory.createKey(contestantKey, getKind(), obj.getContest().getContestId());
		Entity entity = new Entity(key);
		entity.setProperty("contest", DatastoreContestRepository.buildKey(obj.getContest()));
		return entity;
	}

	@Override
	protected UserQuestion fromEntity(Entity entity) {
		Key contestantKey = entity.getKey().getParent();
		User contestant = userRepo.loadUserByUsername(contestantKey.getName());
		
		Key contestKey = (Key) entity.getProperty("contest");
		Contest contest = contestRepo.getContest(contestKey.getParent().getName(), contestKey.getName());
		
		UserQuestion uq = new UserQuestion(contestant, contest);
		return uq;
	}

	
}
