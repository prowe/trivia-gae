package com.rowe.trivia.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.strategy.ContestantSelectionStrategy;
import com.rowe.trivia.strategy.impl.EveryoneContestantSelectionStrategy;

@Configurable
@PersistenceCapable
public class Contest {
	private static Logger logger = LoggerFactory.getLogger(Contest.class);
			
	@Autowired
	private transient ContestRepository repo;
	
	private static ContestantSelectionStrategy selectionStrategy = new EveryoneContestantSelectionStrategy();
	
	/*
	 * To select contestents.
	 * 
	 * Sort the users according to "how much they need a question"
	 * loop over them
	 * for each:
	 * 	If the user is eligible.
	 * 	Calculate the cost and subtract it from the budget.
	 * 	
	 * stop when out of users or budget exausted.
	 */
	@PrimaryKey
	private User sponsor;
	@PrimaryKey
	private String contestId;
	
	private String question;
	private String correctAnswer;
	private List<String> possibleAnswers;
	
	private DateTime startTime;
	private DateTime endTime;
	
	//prize info
	private String prizeDescription;
	private BigDecimal prizeValue;
	private Integer prizeQuantity;
	
	public void completeCreation() {
		if(contestId == null){
			contestId = UUID.randomUUID().toString();
		}
		//set the sponsor
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Unable to set sponsor: no user signed in");
        }
		sponsor = (User) authentication.getPrincipal();
		
		//make sure the possible answers contains the correct answers
		if(possibleAnswers != null && !possibleAnswers.contains(correctAnswer)){
			possibleAnswers.add(correctAnswer);
		}
		//shuffle the answers
		if(possibleAnswers != null){
			Collections.shuffle(possibleAnswers);
		}
		
	}
	
	public void save(){
		repo.save(this);
	}
	
	/**
	 * Is the given user eligible to be entered into this contest?
	 * @param user
	 * @return
	 */
	public boolean isElgible(User user){
		if(ObjectUtils.equals(sponsor, user)){
			return false;
		}
		return true;
	}
	
	/**
	 * Start this contest
	 */
	public void start(){
		logger.info("Starting contest {}", this);
		startTime = new DateTime();
		if(endTime == null){
			endTime = startTime.plusMinutes(10);
		}
		selectionStrategy.selectContestants(this);
		logger.info("Contest started: {}", this);
	}
	
	/**
	 * End this contest
	 */
	public void end(){
		logger.info("Ending contest {}", this);
		
		logger.info("Contest Ended");
	}
	
	public User getSponsor() {
		return sponsor;
	}
	public String getContestId() {
		return contestId;
	}
	public String getQuestion() {
		return question;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public List<String> getPossibleAnswers() {
		return possibleAnswers;
	}
	public void setSponsor(User sponsor) {
		this.sponsor = sponsor;
	}
	public void setContestId(String contestId) {
		this.contestId = contestId;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public void setPossibleAnswers(List<String> possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}

	public boolean isCorrect(String choosenAnswer) {
		return StringUtils.equalsIgnoreCase(choosenAnswer, correctAnswer);
	}

	public String getPrizeDescription() {
		return prizeDescription;
	}

	public Integer getPrizeQuantity() {
		return prizeQuantity;
	}

	public void setPrizeDescription(String prizeDescription) {
		this.prizeDescription = prizeDescription;
	}

	public void setPrizeQuantity(Integer prizeQuantity) {
		this.prizeQuantity = prizeQuantity;
	}
	
	public DateTime getStartTime() {
		return startTime;
	}
}
