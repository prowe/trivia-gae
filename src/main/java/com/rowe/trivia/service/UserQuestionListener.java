package com.rowe.trivia.service;

import com.rowe.trivia.domain.UserQuestion;

/**
 * Indicates that this class should be notified of various events
 * 
 * @author paulrowe
 *
 */
public interface UserQuestionListener {

	/**
	 * Fired when a new {@link UserQuestion} is available to be answered
	 * @param userQuestion
	 */
	public void questionAvailable(UserQuestion userQuestion);
	
	/**
	 * Fired when the provided {@link UserQuestion} has been selected as one of the winners of its Contest
	 * @param userQuestion
	 */
	public void selectedAsWinningQuestion(UserQuestion userQuestion);
}
