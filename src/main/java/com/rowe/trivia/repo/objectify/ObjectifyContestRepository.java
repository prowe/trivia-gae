package com.rowe.trivia.repo.objectify;

import com.googlecode.objectify.ObjectifyService;
import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.repo.ContestRepository;

public class ObjectifyContestRepository extends ObjectifyRepositorySupport<Contest> implements ContestRepository{

	@Override
	public Contest getContest(String userName, String contestId) {
		return ObjectifyService.ofy()
			.load()
			.type(Contest.class)
			.id(contestId)
			.now();
	}

	@Override
	protected Class<Contest> getEntityType() {
		return Contest.class;
	}

}
