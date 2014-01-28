package com.rowe.trivia.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.rowe.trivia.repo.BetterRef;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.UserQuestionRepository;
import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.service.EmailService;

@Configurable
@Entity
public class Contest implements Serializable{
	private static final long serialVersionUID = 6204432236658802049L;

	private static Logger logger = LoggerFactory.getLogger(Contest.class);
			
	@Autowired @Ignore
	private transient ContestRepository repo;
	@Autowired @Ignore
	private transient UserRepository userRepo;
	@Autowired @Ignore
	private transient UserQuestionRepository userQuestionRepo;
	@Autowired @Ignore
	private transient EmailService emailService;
	
	@Id
	private String contestId;
	
	private Ref<User> sponsor;
	private Ref<Question> question;
	
	@NotNull
	private Period duration;
	
	private DateTime startTime;
	private DateTime endTime;
	
	private Prize prize;
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
			endTime = startTime.plus(duration);
		}
		selectContestants();
		scheduleEndContest();
		logger.info("Contest started: {}", this);
	}
	
	private void scheduleEndContest() {
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(Builder.withUrl("/contests/" + contestId + "/stop.html")
			//.param("contestId", contestId)
			.method(Method.POST)
			.etaMillis(endTime.getMillis()));
		
	}

	//TODO: move this to be a map reduce
	private void selectContestants() {
		logger.info("Starting contestant selection for {}", this);
		int usersEntered = 0;
		for(User user:userRepo.listAll()){
			if(isElgible(user)){
				UserQuestion uq = new UserQuestion(user, this);
				emailService.notifyUserOfNewQuestionIfNeeded(uq);
				userQuestionRepo.save(uq);
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
			emailService.notifyUserOfWinningQuestion(winner);
			
		}
		logger.info("Contest Ended. {} winners selected", winners.size());
	}
	public Prize getPrize() {
		return prize;
	}
	public void setPrize(Prize prize) {
		this.prize = prize;
	}
	public User getSponsor() {
		return sponsor == null ? null : sponsor.get();
	}
	public String getContestId() {
		return contestId;
	}
	public Question getQuestion() {
		return question == null ? null : question.get();
	}
	public void setSponsor(User sponsor) {
		this.sponsor = BetterRef.create(sponsor);
	}
	public void setContestId(String contestId) {
		this.contestId = contestId;
	}
	public void setQuestion(Question question) {
		this.question = BetterRef.create(question);
	}
	public void setDuration(Period duration) {
		this.duration = duration;
	}
	public Period getDuration() {
		return duration;
	}

	public Integer getPrizeQuantity() {
		return prizeQuantity;
	}

	public void setPrizeQuantity(Integer prizeQuantity) {
		this.prizeQuantity = prizeQuantity;
	}
	
	public DateTime getStartTime() {
		return startTime;
	}
	public DateTime getEndTime() {
		return endTime;
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

	/**
	 * Has this contest been started and not ended
	 * @return
	 */
	public boolean isInProgress() {
		DateTime now = new DateTime();
		return startTime != null
			&& now.isAfter(startTime)
			&& (endTime == null || now.isBefore(endTime));
	}
}
