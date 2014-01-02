package com.rowe.trivia.repo;

import com.rowe.trivia.domain.Contest;

public interface ContestRepository extends BaseRepository<Contest>{

	public Contest getContest(String userName, String contestId);
	
}
