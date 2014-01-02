package com.rowe.trivia.strategy;

import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;

/**
 * Strategy for notifying {@link User}s of various events
 * @author paulrowe
 *
 */
public interface UserNotificationStrategy {

	/**
	 * Notify the contestent that a new question has been asked of them and is available to be answered 
	 * @param userQuestion
	 */
	public void questionAsked(UserQuestion userQuestion);
}