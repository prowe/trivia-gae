package com.rowe.trivia.strategy;

import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.User;

/**
 * Strategy for selecting which {@link User}s should be entered into a {@link Contest}
 * @author paulrowe
 *
 */
public interface ContestantSelectionStrategy {

	public void selectContestants(Contest contest);
}
