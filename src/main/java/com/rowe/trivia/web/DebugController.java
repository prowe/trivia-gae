package com.rowe.trivia.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rowe.trivia.domain.User;
import com.rowe.trivia.repo.UserRepository;

@Controller
@RequestMapping("/debug")
public class DebugController {
	private static Logger logger = LoggerFactory.getLogger(DebugController.class);

	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping(value="/createUser.html")
	public String createUser(@ModelAttribute User user){
		user.setPassword("password");
		userRepo.save(user);
		logger.info("Created user {}", user);
		return "redirect:/";
	}
	
}
