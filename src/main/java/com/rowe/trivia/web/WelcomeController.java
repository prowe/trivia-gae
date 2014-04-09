package com.rowe.trivia.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.Question;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.UserQuestionRepository;
import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.strategy.ContestSelectionStrategy;
import com.rowe.trivia.strategy.QuestionSelectionStrategy;

@Controller
public class WelcomeController {
	private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ContestRepository contestRepo;
	@Autowired
	private UserQuestionRepository userQuestionRepo;
	@Autowired
	private ContestSelectionStrategy contestSelectionStrategy;
	@Autowired
	private QuestionSelectionStrategy questionSelectionStrategy;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
    public String root(Map<String, Object> modelMap) {
		if(User.currentUser() != null){
			logger.info("User logged in");
			return "redirect:home.html";
		}else{
			return "signin";
		}
    }
	
	@RequestMapping("/home.html")
	public void home(Map<String, Object> modelMap){
		UserQuestion currentQuestion = findOrCreateUserQuestion();
		modelMap.put("currentQuestion", currentQuestion);
	}

	private UserQuestion findOrCreateUserQuestion() {
		User currentUser = User.currentUser();
		List<UserQuestion> available = userQuestionRepo.findAvailableForUser(currentUser);
		if(!available.isEmpty()){
			return available.get(0);
		}
		//create new
		logger.info("Creating new UserQuestion for {}", currentUser);
		Contest contest = contestSelectionStrategy.selectContest(currentUser);
		Question question = questionSelectionStrategy.selectQuestion(currentUser);
		
		UserQuestion uq = new UserQuestion(currentUser, question, contest);
		userQuestionRepo.save(uq);
		return uq;
	}
}
