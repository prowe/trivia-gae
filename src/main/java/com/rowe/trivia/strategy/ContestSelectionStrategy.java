package com.rowe.trivia.strategy;

import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.User;

public interface ContestSelectionStrategy {

	public Contest selectContest(User user);
}
