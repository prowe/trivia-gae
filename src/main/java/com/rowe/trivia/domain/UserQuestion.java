package com.rowe.trivia.domain;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Configurable;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.condition.IfNotNull;
import com.rowe.trivia.repo.BetterRef;

@Configurable
@Entity
public class UserQuestion {
	private static Random random = new Random();
	public static enum Status {
		ASKED,
		SKIPPED,
		CORRECT,
		INCORRECT;
	}
	
	@Parent
	private Ref<User> contestant;
	@Id
	private String id;
	
	
	@Index
	private Ref<Contest> contest;
	private Ref<Question> question;

	private String choosenAnswer;
	@Index
	private Status status;
	private DateTime askedDate;
	private DateTime answerDate;
	
	@Index(IfNotNull.class)
	private Integer correctAnswerTicket;
	
	private boolean winner = false;
	private int numberOfEntries = 0;
	
	public UserQuestion(User contestant, Question question, Contest contest) {
		this.contestant = BetterRef.create(contestant);
		this.contest = BetterRef.create(contest);
		this.question = BetterRef.create(question);
		this.id = question.getQuestionId();
		markAsked();
	}

	private void markAsked() {
		this.askedDate = new DateTime();
		this.status = Status.ASKED;
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
	public Question getQuestion(){
		return question == null ? null : question.get();
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
			issueCorrectAnswerTickets(1);
			status = Status.CORRECT;
		}else{
			status = Status.INCORRECT;
		}
	}
	public void skipQuestion(){
		answerDate = new DateTime();
		status = Status.SKIPPED;
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
	public boolean isAvailable() {
		return status == Status.ASKED 
			&& getContest().isInProgress();
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
	private void issueCorrectAnswerTickets(int numberOfEntries){
		if(numberOfEntries <= 0){
			throw new IllegalArgumentException("Illegal entries count " + numberOfEntries);
		}
		for(int i=0; i<numberOfEntries; i++){
			issueCorrectAnswerTicket();
		}
	}
	
	public String getChoosenAnswer() {
		return choosenAnswer;
	}

	public Period getRemainingTime(){
		return new Period(new DateTime(), getContest().getEndTime());
	}

	public DateTime getAskedDate() {
		return askedDate;
	}
	public Status getStatus() {
		return status;
	}

}
