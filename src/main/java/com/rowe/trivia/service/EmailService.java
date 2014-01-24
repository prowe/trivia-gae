package com.rowe.trivia.service;

import com.rowe.trivia.domain.UserQuestion;


public interface EmailService  {

	/**
	 * Fired when a new {@link UserQuestion} is available to be answered
	 * @param userQuestion
	 */
	public void notifyUserOfNewQuestionIfNeeded(UserQuestion userQuestion);
	
	
	/**
	 * Fired when the provided {@link UserQuestion} has been selected as one of the winners of its Contest
	 * @param userQuestion
	 */
	public void notifyUserOfWinningQuestion(UserQuestion userQuestion);
}
