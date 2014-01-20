package com.rowe.trivia.repo.objectify;

import com.googlecode.objectify.ObjectifyService;
import com.rowe.trivia.domain.Question;
import com.rowe.trivia.repo.QuestionRepository;

public class ObjectifyQuestionRepository extends ObjectifyRepositorySupport<Question>
	implements QuestionRepository{

	@Override
	protected Class<Question> getEntityType() {
		return Question.class;
	}
	
	@Override
	public Question getById(String id) {
		return ObjectifyService.ofy()
			.load()
			.type(Question.class)
			.id(id)
			.now();
	}
}
