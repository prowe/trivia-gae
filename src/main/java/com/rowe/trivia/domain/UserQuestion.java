package com.rowe.trivia.domain;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.condition.IfNotNull;
import com.rowe.trivia.convert.ExpirationTimePrinter;
import com.rowe.trivia.repo.BetterRef;
import com.rowe.trivia.repo.UserQuestionRepository;

@Configurable
@Entity
public class UserQuestion {
	private static Random random = new Random();
	
	@Autowired @Ignore
	private transient UserQuestionRepository repo;
	
	@Parent
	private Ref<User> contestant;
	@Id
	private String contestId;
	@Index
	private Ref<Contest> contest;

	private String choosenAnswer;

	private DateTime answerDate;
	
	@Index(IfNotNull.class)
	private Integer correctAnswerTicket;
	
	public UserQuestion(User contestant, Contest contest) {
		this.contestant = BetterRef.create(contestant);
		this.contest = BetterRef.create(contest);
		this.contestId = contest.getContestId();
	}
	
	//added for Objectify
	@SuppressWarnings("unused")
	private UserQuestion(){}
	
	@Override
	public String toString() {
		return "UserQuestion [contestant=" + getContestant() + ", contest="
				+ getContest() + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getContest())
			.append(getContestant())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof UserQuestion) {
			UserQuestion right = (UserQuestion) obj;
			return new EqualsBuilder()
				.append(getContest(), right.getContest())
				.append(getContestant(), right.getContestant())
				.isEquals();
		}
		return false;
	}

	public Contest getContest() {
		return contest == null ? null : contest.get();
	}
	public User getContestant() {
		return contestant == null ? null : contestant.get();
	}

	public void save() {
		repo.save(this);
	}

	public void answerQuestion(String answer) {
		if(isAnswered()){
			throw new IllegalStateException("UserQuestion already answered: " + this);
		}
		if(!getContest().isInProgress()){
			throw new IllegalStateException("Contest not in progress for user question: " + this);
		}
		choosenAnswer = answer;
		answerDate = new DateTime();
		if(isCorrect()){
			issueCorrectAnswerTicket();
		}
	}

	public boolean isCorrect(){
		return getQuestion().isCorrect(choosenAnswer);
	}
	/**
	 * Has the contestant answered this question?
	 * @return
	 */
	public boolean isAnswered(){
		return answerDate != null;
	}
	public boolean isWinner(){
		List<UserQuestion> winningAnswers = getContest().getWinningAnswers();
		if(winningAnswers != null && winningAnswers.contains(this)){
			return true;
		}
		return false;
	}
	
	public DateTime getAnswerDate() {
		return answerDate;
	}
	public Integer getCorrectAnswerTicket() {
		return correctAnswerTicket;
	}
	
	private void issueCorrectAnswerTicket() {
		int newTicket = random.nextInt();
		if(correctAnswerTicket == null || correctAnswerTicket>newTicket){
			correctAnswerTicket = newTicket;
		}
	}
	
	public String getChoosenAnswer() {
		return choosenAnswer;
	}
	
	public Question getQuestion(){
		return getContest().getQuestion();
	}
	
	/**
	 * TODO: clean this up, should be in a JSP tag
	 * @return
	 */
	public String getFormattedExpirationTime(){
		return new ExpirationTimePrinter().print(getContest().getEndTime(), Locale.getDefault());
	}

	/**
	 * Returns true if this question has not yet been answered and it has not expired
	 * @return
	 */
	public boolean isAvailable() {
		return !isAnswered() && getContest().isInProgress();
	}
	
}
