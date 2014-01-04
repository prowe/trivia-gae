package com.rowe.trivia.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.UserQuestionRepository;
import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.strategy.UserNotificationStrategy;

@Configurable
@Entity
public class Contest {
	private static Logger logger = LoggerFactory.getLogger(Contest.class);
			
	@Autowired @Ignore
	private transient ContestRepository repo;
	@Autowired @Ignore
	private transient UserRepository userRepo;
	@Autowired @Ignore
	private transient UserQuestionRepository userQuestionRepo;
	@Autowired @Ignore
	private transient UserNotificationStrategy notificationStrategy;
	
	@Id
	private String contestId;
	
	private Ref<User> sponsor;
	private String question;
	private String correctAnswer;
	private List<String> possibleAnswers;
	
	private DateTime startTime;
	private DateTime endTime;
	
	//prize info
	private String prizeDescription;
	private BigDecimal prizeValue;
	private Integer prizeQuantity;
	
	private List<Ref<UserQuestion>> winningAnswers;
	
	public void completeCreation() {
		if(contestId == null){
			contestId = UUID.randomUUID().toString();
		}
		//set the sponsor
		User currentUser = User.currentUser();
        if (currentUser == null) {
            throw new IllegalStateException("Unable to set sponsor: no user signed in");
        }
		setSponsor(currentUser);
		
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
		if(ObjectUtils.equals(getSponsor(), user)){
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
		selectContestants();
		logger.info("Contest started: {}", this);
	}
	
	private void selectContestants() {
		logger.info("Starting contestant selection for {}", this);
		int usersEntered = 0;
		for(User user:userRepo.listAll()){
			if(isElgible(user)){
				UserQuestion uq = new UserQuestion(user, this);
				userQuestionRepo.save(uq);
				notificationStrategy.questionAsked(uq);
				usersEntered++;
			}
		}
		logger.info("Contestant selection done for {}. {} users entered", this, usersEntered);
	}
	
	/**
	 * End this contest
	 */
	public void end(){
		logger.info("Ending contest {}", this);
		List<UserQuestion> winners = userQuestionRepo.findWinners(this, prizeQuantity);
		winningAnswers = new ArrayList<Ref<UserQuestion>>();
		for(UserQuestion winner:winners){
			winningAnswers.add(Ref.create(winner));
			notificationStrategy.choosenAsWinner(winner);
		}
		logger.info("Contest Ended. {} winners selected", winners.size());
	}
	
	public User getSponsor() {
		return sponsor == null ? null : sponsor.get();
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
		this.sponsor = Ref.create(sponsor);
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
	
	public List<UserQuestion> getWinningAnswers() {
		if(winningAnswers == null){
			return null;
		}
		List<UserQuestion> list = new ArrayList<UserQuestion>();
		for(Ref<UserQuestion> win:winningAnswers){
			list.add(win.get());
		}
		return Collections.unmodifiableList(list);
	}
}
