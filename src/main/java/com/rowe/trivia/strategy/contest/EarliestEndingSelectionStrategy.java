package com.rowe.trivia.strategy.contest;

import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.Prize;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.strategy.ContestSelectionStrategy;

public class EarliestEndingSelectionStrategy implements ContestSelectionStrategy{
	private static Logger logger = LoggerFactory.getLogger(EarliestEndingSelectionStrategy.class);

	@Autowired
	private ContestRepository contestRepo;
	
	public Contest selectContest(User user) {
		List<Contest> inProgressContests = contestRepo.findInProgressContests();
		Collections.sort(inProgressContests, Contest.END_TIME_COMPARATOR);
		if(!inProgressContests.isEmpty()){
			Contest contest  = inProgressContests.get(0);
			logger.info("Selecting in progress contest {} for user {}", contest, user);
			return contest;
		}else{
			Contest contest  = createNewContest();
			logger.info("Selecting new contest {} for user {}", contest, user);
			return contest;
		}
	}

	private Contest createNewContest() {
		Contest contest = new Contest(
			DateTime.now(), 
			DateTime.now().plusMinutes(1), 
			new Prize("$1 Amazon Gift Card"), 
			1);
		contestRepo.save(contest);
		contest.scheduleEnd();
		return contest;
	}
	
}
