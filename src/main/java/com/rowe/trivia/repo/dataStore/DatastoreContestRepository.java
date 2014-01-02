package com.rowe.trivia.repo.dataStore;

import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.UserRepository;

public class DatastoreContestRepository extends DatastoreRepositorySupport<Contest> implements ContestRepository{
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	protected Class<Contest> getEntityType() {
		return Contest.class;
	}
	
	@Override
	public Contest getContest(String userName, String contestId) {
		return load(buildKey(userName, contestId));
	}
	
	private static Key buildKey(String userName, String contestId) {
		Key sponsorKey = KeyFactory.createKey(User.class.getSimpleName(), userName);
		return KeyFactory.createKey(sponsorKey, Contest.class.getSimpleName(), contestId);
	}

	static Key buildKey(Contest contest){
		return buildKey(contest.getSponsor().getUsername(), contest.getContestId());
	}
	
	@Override
	protected Entity toEntity(Contest contest) {
		Key sponsorKey = KeyFactory.createKey(User.class.getSimpleName(), contest.getSponsor().getUsername());
		Entity entity = new Entity(getKind(), contest.getContestId(), sponsorKey);
		entity.setProperty("question", contest.getQuestion());
		entity.setProperty("correctAnswer", contest.getCorrectAnswer());
		entity.setProperty("possibleAnswers", contest.getPossibleAnswers());
		return entity;
	}
	
	@Override
	protected Contest fromEntity(Entity entity) {
		Contest contest = new Contest();
		User sponsor = userRepo.loadUserByUsername(entity.getKey().getParent().getName());
		contest.setSponsor(sponsor);
		contest.setContestId(entity.getKey().getName());

		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(contest);
		Map<String, Object> properties = entity.getProperties();
		wrapper.setPropertyValues(properties);
		return contest;
	}

	public Contest load(Key key) {
		try {
			Entity entity = datastoreService.get(key);
			return fromEntity(entity);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
}
