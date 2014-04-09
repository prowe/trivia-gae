package com.rowe.trivia.strategy;

import com.rowe.trivia.domain.Question;
import com.rowe.trivia.domain.User;

public interface QuestionSelectionStrategy {

	public Question selectQuestion(User user);
}
