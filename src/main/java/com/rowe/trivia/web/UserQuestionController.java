package com.rowe.trivia.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.repo.UserQuestionRepository;

@Controller
@RequestMapping("/questions")
public class UserQuestionController {
	private static Logger logger = LoggerFactory.getLogger(UserQuestionController.class);
	
	@Autowired
	private UserQuestionRepository questionRepo;
	
	@RequestMapping(value="{userName}/{contestId}/answer.html", method=RequestMethod.GET)
	public String answerQuestion(
		@PathVariable("userName") String username,
		@PathVariable("contestId") String contestId,
		Map<String, Object> modelMap	
		){
		UserQuestion uq = questionRepo.findByUsernameContest(username, contestId);
		logger.info("Prompting question {}", uq);
		modelMap.put("userQuestion", uq);
		
		return "questions/answer";
	}
	
	@RequestMapping(value="{userName}/{contestId}/answer.html", method=RequestMethod.POST)
	public String submitAnswer(
		@PathVariable("userName") String username,
		@PathVariable("contestId") String contestId,
		@RequestParam("answer") String answer,
		Map<String, Object> modelMap	
		){
		UserQuestion uq = questionRepo.findByUsernameContest(username, contestId);
		logger.info("answering question {}", uq);
		uq.answerQuestion(answer);
		modelMap.put("userQuestion", uq);
		return "questions/answerSubmitted";
	}
}
