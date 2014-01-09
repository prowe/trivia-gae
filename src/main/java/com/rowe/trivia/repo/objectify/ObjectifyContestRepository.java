package com.rowe.trivia.repo.objectify;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.ObjectifyService;
import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.repo.ContestRepository;

public class ObjectifyContestRepository extends ObjectifyRepositorySupport<Contest> implements ContestRepository{

	@Override
	public Contest getContest(String contestId) {
		return ObjectifyService.ofy()
			.load()
			.type(Contest.class)
			.id(contestId)
			.now();
	}
	
	@Override
	public List<Contest> findInProgressContests() {
		QueryResultIterable<Contest> iterable = ObjectifyService.ofy()
			.load()
			.type(Contest.class)
			.iterable();
		List<Contest> inProgress = new ArrayList<Contest>();
		for(Contest c:iterable){
			if(c.isInProgress()){
				inProgress.add(c);
			}
		}
		return inProgress;
	}

	@Override
	protected Class<Contest> getEntityType() {
		return Contest.class;
	}

}
