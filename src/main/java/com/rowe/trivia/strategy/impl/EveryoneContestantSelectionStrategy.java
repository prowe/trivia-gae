package com.rowe.trivia.strategy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.strategy.ContestantSelectionStrategy;

/**
 * Loop over everyone and let them be a contestant
 * @author paulrowe
 *
 */
@Configurable
public class EveryoneContestantSelectionStrategy implements ContestantSelectionStrategy {
	private static Logger logger = LoggerFactory.getLogger(EveryoneContestantSelectionStrategy.class);

	@Autowired
	private transient UserRepository userRepo;
	
	public void selectContestants(Contest contest) {
		logger.info("Starting contestant selection for {}", contest);
		int usersEntered = 0;
		for(User user:userRepo.listAll()){
			if(contest.isElgible(user)){
				UserQuestion uq = new UserQuestion(user, contest);
				uq.save();
				usersEntered++;
			}
		}
		logger.info("Contestant selection done for {}. {} users entered", contest, usersEntered);
	}
}
