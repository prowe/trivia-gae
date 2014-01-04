package com.rowe.trivia.domain;

import java.util.Random;


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
	
	@SuppressWarnings("unused")
	private UserQuestion(){}
	
	@Override
	public String toString() {
		return "UserQuestion [contestant=" + contestant + ", contest="
				+ contest + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contest == null) ? 0 : contest.hashCode());
		result = prime * result
				+ ((contestant == null) ? 0 : contestant.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UserQuestion)) {
			return false;
		}
		UserQuestion other = (UserQuestion) obj;
		if (contest == null) {
			if (other.contest != null) {
				return false;
			}
		} else if (!contest.equals(other.contest)) {
			return false;
		}
		if (contestant == null) {
			if (other.contestant != null) {
				return false;
			}
		} else if (!contestant.equals(other.contestant)) {
			return false;
		}
		return true;
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
		choosenAnswer = answer;
		answerDate = new DateTime();
		if(isCorrect()){
			issueCorrectAnswerTicket();
		}
	}

	public boolean isCorrect(){
		return getContest().isCorrect(choosenAnswer);
	}
	
	public DateTime getAnswerDate() {
		return answerDate;
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
}
