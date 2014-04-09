package com.rowe.trivia.repo;

import java.util.List;

import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;

public interface UserQuestionRepository extends BaseRepository<UserQuestion>{

	public UserQuestion findByUserAndId(User currentUser, String id);
	
	public List<UserQuestion> findByUser(User user);
	
	/**
	 * Return a list of {@link UserQuestion} that the provided user is able to answer
	 * @param currentUser
	 * @return
	 */
	public List<UserQuestion> findAvailableForUser(User user);
	
	/**
	 * Return at most maxCount {@link UserQuestion} objects that have non null winningTickets ordered by ticketNumber
	 * @param contest
	 * @param maxCount
	 * @return
	 */
	public List<UserQuestion> findWinners(Contest contest, int maxCount);

	/**
	 * Find a list of the {@link UserQuestion} the the provided user has won
	 * @param currentUser
	 * @return
	 */
	public List<UserQuestion> findWinningByUser(User currentUser);

	/**
	 * Return list of {@link UserQuestion}s the provided user has answered
	 * @param currentUser
	 * @return
	 */
	public Object findAnsweredByUser(User currentUser);

	

	
}
