package com.rowe.trivia.repo;

import java.util.List;

import com.rowe.trivia.domain.Contest;

public interface ContestRepository extends BaseRepository<Contest>{

	public Contest getContest(String contestId);
	
	public List<Contest> findInProgressContests();
}
