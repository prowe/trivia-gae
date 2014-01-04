package com.rowe.trivia.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rowe.trivia.domain.User;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.UserQuestionRepository;
import com.rowe.trivia.repo.UserRepository;

@Controller
public class WelcomeController {
	private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ContestRepository contestRepo;
	@Autowired
	private UserQuestionRepository userQuestionRepo;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
    public String root(Map<String, Object> modelMap) {
		if(User.currentUser() != null){
			logger.info("User logged in");
			modelMap.put("userQuestionList", userQuestionRepo.findByUser(User.currentUser()));
			return "home";
		}else{
			modelMap.put("userList", userRepo.listAll().iterator());
			return "signin";
		}
    }
}
