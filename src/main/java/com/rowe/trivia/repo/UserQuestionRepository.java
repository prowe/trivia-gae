package com.rowe.trivia.repo;

import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;

public interface UserQuestionRepository extends BaseRepository<UserQuestion>{

	public UserQuestion findByUsernameContest(String username, String contestId);

	public Iterable<UserQuestion> findByUser(User user);
}
