package com.rowe.trivia.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Configurable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Configurable
@Entity
public class Question {
	@Id
	private String questionId;

	@NotBlank
	private String question;
	@NotBlank
	private String correctAnswer;
	@Size(min=1, max=3)
	private List<String> possibleAnswers;
	
	public Question(String text, String correct, String...wrong){
		this();
		setQuestion(text);
		setCorrectAnswer(correct);
		setPossibleAnswers(new ArrayList<String>(Arrays.asList(wrong)));
		completeCreation();
	}
	public Question() {
		questionId = UUID.randomUUID().toString();
	}
	
	public void completeCreation(){
		//make sure the possible answers contains the correct answers
		if(possibleAnswers != null && !possibleAnswers.contains(correctAnswer)){
			possibleAnswers.add(correctAnswer);
		}
		//shuffle the answers
		if(possibleAnswers != null){
			Collections.shuffle(possibleAnswers);
		}
	}
	
	public boolean isCorrect(String choosenAnswer) {
		return StringUtils.equalsIgnoreCase(choosenAnswer, correctAnswer);
	}
	
	public String getQuestionId() {
		return questionId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public List<String> getPossibleAnswers() {
		return possibleAnswers;
	}
	public void setPossibleAnswers(List<String> possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}
}
