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

import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.repo.UserQuestionRepository;

@Controller
@RequestMapping("/questions")
public class UserQuestionController {
	private static Logger logger = LoggerFactory.getLogger(UserQuestionController.class);
	
	@Autowired
	private UserQuestionRepository questionRepo;
	
	@RequestMapping(value="{contestId}/answer.html", method=RequestMethod.GET)
	public String answerQuestion(
		@PathVariable("contestId") String contestId,
		Map<String, Object> modelMap	
		){
		UserQuestion uq = questionRepo.findByUserContest(User.currentUser(), contestId);
		logger.info("Prompting question {}", uq);
		modelMap.put("userQuestion", uq);
		
		return "questions/answer";
	}
	
	@RequestMapping(value="history.html")
	public void history(Map<String, Object> modelMap){
		modelMap.put("userQuestionList", questionRepo.findAnsweredByUser(User.currentUser()));
	}
	
	@RequestMapping(value="winning.html")
	public void winning(Map<String, Object> modelMap){
		modelMap.put("userQuestionList", questionRepo.findWinningByUser(User.currentUser()));
	}
	
	@RequestMapping(value="{contestId}/shareViaTwitter.html", method=RequestMethod.POST)
	public String shareViaTwitter(
		@PathVariable("contestId") String contestId){
		UserQuestion uq = questionRepo.findByUserContest(User.currentUser(), contestId);
		logger.info("Sharing on twitter {}", uq);
		uq.shareViaTwitter();
		return "redirect:/#" + uq.getContest().getContestId();
	}
	
	@RequestMapping(value="{contestId}/answer.html", method=RequestMethod.POST)
	public String submitAnswer(
		@PathVariable("contestId") String contestId,
		@RequestParam("answer") String answer,
		Map<String, Object> modelMap	
		){
		UserQuestion uq = questionRepo.findByUserContest(User.currentUser(), contestId);
		logger.info("answering question {}", uq);
		uq.answerQuestion(answer);
		questionRepo.save(uq);
		
		//modelMap.put("userQuestion", uq);
		//return "questions/answerSubmitted";
		return "redirect:/#" + uq.getContest().getContestId();
	}
	
	//TODO: combine these methods
	@RequestMapping(value="{contestId}/answer.json", method=RequestMethod.POST)
	public String submitAnswerJson(
		@PathVariable("contestId") String contestId,
		@RequestParam("answer") String answer,
		Map<String, Object> modelMap	
		){
		UserQuestion uq = questionRepo.findByUserContest(User.currentUser(), contestId);
		logger.info("answering question {}", uq);
		uq.answerQuestion(answer);
		questionRepo.save(uq);
		
		modelMap.put("userQuestion", uq);
		return "questions/answerSubmitted";
	}
}
