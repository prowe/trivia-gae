package com.rowe.trivia.repo.objectify;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
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
	public Iterable<UserQuestion> findByUser(User user) {
		return ObjectifyService.ofy()
			.load()
			.type(UserQuestion.class)
			.ancestor(user)
			.list();
	}

	@Override
	protected Class<UserQuestion> getEntityType() {
		return UserQuestion.class;
	}

}
