package com.rowe.trivia.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
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
import com.googlecode.objectify.annotation.Index;
import com.rowe.trivia.repo.UserQuestionRepository;

@Configurable
@Entity
public class Contest implements Serializable{
	private static final long serialVersionUID = 6204432236658802049L;
	public static final Comparator<? super Contest> END_TIME_COMPARATOR = new Comparator<Contest>() {
		@Override
		public int compare(Contest o1, Contest o2) {
			return o1.getEndTime().compareTo(o2.getEndTime());
		}
	};
	
	private static Logger logger = LoggerFactory.getLogger(Contest.class);
			
	@Autowired @Ignore
	private transient UserQuestionRepository userQuestionRepo;
	
	@Id
	private String contestId;
	
	@Index
	private DateTime startTime;
	@Index
	private DateTime endTime;
	
	private Prize prize;
	private Integer prizeQuantity;
	
	private List<Ref<UserQuestion>> winningAnswers;
	
	public Contest() {
		contestId = UUID.randomUUID().toString();
	}
	
	public Contest(DateTime startTime, DateTime endTime, Prize prize,
			Integer prizeQuantity) {
		this();
		this.startTime = startTime;
		this.endTime = endTime;
		this.prize = prize;
		this.prizeQuantity = prizeQuantity;
	}
	
	public void scheduleEnd() {
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(Builder.withUrl("/contests/" + contestId + "/end.html")
			.method(Method.POST)
			.etaMillis(endTime.getMillis() + 10000));
		
	}

	public void end(){
		logger.info("Ending contest {}", this);
		List<UserQuestion> winners = userQuestionRepo.findWinners(this, prizeQuantity);
		winningAnswers = new ArrayList<Ref<UserQuestion>>();
		for(UserQuestion winner:winners){
			winningAnswers.add(Ref.create(winner));
		}
		logger.info("Contest Ended. {} winners selected", winners.size());
	}
	
	public Prize getPrize() {
		return prize;
	}
	public void setPrize(Prize prize) {
		this.prize = prize;
	}
	public String getContestId() {
		return contestId;
	}
	public void setContestId(String contestId) {
		this.contestId = contestId;
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

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}
}
