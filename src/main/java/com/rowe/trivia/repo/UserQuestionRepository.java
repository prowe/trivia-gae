package com.rowe.trivia.repo;

import java.util.List;

import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;

public interface UserQuestionRepository extends BaseRepository<UserQuestion>{

	public UserQuestion findByUsernameContest(String username, String contestId);

	public List<UserQuestion> findByUser(User user);
	
	/**
	 * Return at most maxCount {@link UserQuestion} objects that have non null winningTickets ordered by ticketNumber
	 * @param contest
	 * @param maxCount
	 * @return
	 */
	public List<UserQuestion> findWinners(Contest contest, int maxCount);
}
