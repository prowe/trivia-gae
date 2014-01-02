package com.rowe.trivia.domain;

import java.util.Random;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.rowe.trivia.repo.UserQuestionRepository;

@Configurable
@PersistenceCapable
public class UserQuestion {
	private static Random random = new Random();
	
	@Autowired
	private transient UserQuestionRepository repo;

	@PrimaryKey
	private User contestant;
	@PrimaryKey
	private Contest contest;

	private String choosenAnswer;

	private DateTime answerDate;
	private Integer correctAnswerTicket;
	
	public UserQuestion(User contestant, Contest contest) {
		this.contestant = contestant;
		this.contest = contest;
		
	}
	
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
		return contest;
	}
	public User getContestant() {
		return contestant;
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
		return contest.isCorrect(choosenAnswer);
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
}
