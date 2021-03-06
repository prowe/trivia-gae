package com.rowe.trivia.repo.objectify;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.repo.UserQuestionRepository;

public class ObjectifyUserQuestionRepository extends ObjectifyRepositorySupport<UserQuestion> implements UserQuestionRepository{

	@Override
	public UserQuestion findByUsernameContest(String username, String contestId) {
		return ObjectifyService.ofy()
			.load()
			.type(UserQuestion.class)
			.parent(Key.create(User.class, username))
			.id(contestId)
			.now();
	}
	
	@Override
	public UserQuestion findByUserContest(User user, String contestId) {
		return findByUsernameContest(user.getUsername(), contestId);
	}

	@Override
	public List<UserQuestion> findByUser(User user) {
		return ObjectifyService.ofy()
			.load()
			.type(UserQuestion.class)
			.ancestor(user)
			.list();
	}
	
	@Override
	public List<UserQuestion> findAvailableForUser(User user) {
		List<UserQuestion> results = new ArrayList<UserQuestion>();
		//TODO: push this down into the datastore
		for(UserQuestion uq:findByUser(user)){
			if(uq.isAvailable()){
				results.add(uq);
			}
		}
		return results;
	}
	
	@Override
	public Object findAnsweredByUser(User user) {
		List<UserQuestion> results = new ArrayList<UserQuestion>();
		//TODO: push this down into the datastore
		for(UserQuestion uq:findByUser(user)){
			if(uq.isAnswered()){
				results.add(uq);
			}
		}
		return results;
	}
	
	@Override
	public List<UserQuestion> findWinningByUser(User user) {
		List<UserQuestion> results = new ArrayList<UserQuestion>();
		//TODO: push this down into the datastore
		for(UserQuestion uq:findByUser(user)){
			if(uq.isWinner()){
				results.add(uq);
			}
		}
		return results;
	}
	
	@Override
	public List<UserQuestion> findWinners(Contest contest, int maxCount) {
		 return ObjectifyService.ofy()
			.load()
			.type(UserQuestion.class)
			.filter("contest", contest)
			.order("correctAnswerTicket")
			.limit(maxCount)
			.list();
	}

	@Override
	protected Class<UserQuestion> getEntityType() {
		return UserQuestion.class;
	}

}
