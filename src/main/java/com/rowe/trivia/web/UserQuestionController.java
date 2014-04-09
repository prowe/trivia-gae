package com.rowe.trivia.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriTemplate;

import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.repo.UserQuestionRepository;

@Controller
@RequestMapping("/questions")
public class UserQuestionController {
	private static Logger logger = LoggerFactory.getLogger(UserQuestionController.class);
	
	@Autowired
	private UserQuestionRepository questionRepo;
	
	@RequestMapping(value="{id}/answer.html", method=RequestMethod.POST, params={"answer"})
	public String submitAnswer(
		@PathVariable("id") String id,
		@RequestParam("answer") String answer
		){
		UserQuestion uq = questionRepo.findByUserAndId(User.currentUser(), id);
		logger.info("answering question {}", uq);
		uq.answerQuestion(answer);
		questionRepo.save(uq);
		
		
		return new UriTemplate("redirect:/questions/{id}/answer.html")
			.expand(uq.getQuestion().getQuestionId())
			.toString();
	}
	
	@RequestMapping(value="{id}/answer.html", method=RequestMethod.GET)
	public String submitAnswer(@PathVariable("id") String id, Map<String, Object> modelMap){
		UserQuestion uq = questionRepo.findByUserAndId(User.currentUser(), id);
		modelMap.put("userQuestion", uq);
		return "questions/answerSubmitted";
	}
	
	@RequestMapping(value="{id}/answer.html", method=RequestMethod.POST, params={"skip"})
	public String skip(@PathVariable("id") String id){
		UserQuestion uq = questionRepo.findByUserAndId(User.currentUser(), id);
		logger.info("skipping question {}", uq);
		uq.skipQuestion();
		questionRepo.save(uq);
		
		return new UriTemplate("redirect:/questions/{id}/answer.html")
			.expand(uq.getQuestion().getQuestionId())
			.toString();
	}
	
	@RequestMapping(value="/winning.html", method=RequestMethod.GET)
	public String winners(Map<String, Object> modelMap){
		List<UserQuestion> winningByUser = questionRepo.findWinningByUser(User.currentUser());
		modelMap.put("winningList", winningByUser);
		return "questions/winning";
	}
	
	
}
