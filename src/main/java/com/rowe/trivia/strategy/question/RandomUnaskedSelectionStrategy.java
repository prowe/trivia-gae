package com.rowe.trivia.strategy.question;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rowe.trivia.domain.Question;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.questionGeneration.CitiesByPopulationQuestionGenerator;
import com.rowe.trivia.repo.QuestionRepository;
import com.rowe.trivia.repo.UserQuestionRepository;
import com.rowe.trivia.strategy.QuestionSelectionStrategy;

public class RandomUnaskedSelectionStrategy implements QuestionSelectionStrategy{
	private static Logger logger = LoggerFactory.getLogger(RandomUnaskedSelectionStrategy.class);
			
	@Autowired
	private UserQuestionRepository userQuestionRepo;
	@Autowired
	private QuestionRepository questionRepo;
	
	private List<Question> questionCache;

	@Override
	public Question selectQuestion(User user) {
		Set<String> askedIds = getAskedQuestionIds(user);
		
		List<Question> allQuestions = loadAllQuestions();
		
		if(allQuestions.isEmpty()){
			//populate some testing questions
			allQuestions = populateAndSaveAllQuestions();
		}
		
		Collections.shuffle(allQuestions);
		for(Question q:allQuestions){
			if(!askedIds.contains(q.getQuestionId())){
				logger.info("Select question {} for user {}", q, user);
				return q;
			}
		}
		throw new IllegalStateException("user answered all questions");
	}
	
	private List<Question> loadAllQuestions(){
		if(questionCache != null){
			return questionCache;
		}
		synchronized (this) {
			if(questionCache == null){
				questionCache = questionRepo.listAll();
			}
		}
		return questionCache;
	}
	
	private List<Question> populateAndSaveAllQuestions() {
		List<Question> questions = new CitiesByPopulationQuestionGenerator().generateQuestions();
		for(Question q:questions){
			questionRepo.save(q);
		}
		return questions;
	}

	private Set<String> getAskedQuestionIds(User user){
		List<UserQuestion> asked = userQuestionRepo.findByUser(user);
		Set<String> ids = new HashSet<>();
		for(UserQuestion uq:asked){
			ids.add(uq.getQuestion().getQuestionId());
		}
		return ids;
	}

}
