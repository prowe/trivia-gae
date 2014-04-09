package com.rowe.trivia.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rowe.trivia.domain.StateCode;
import com.rowe.trivia.domain.User;

@Controller
@RequestMapping("/users/myAccount.html")
public class MyAccountController {
	private static final Logger logger = LoggerFactory.getLogger(MyAccountController.class);
	
	@ModelAttribute("user")
	public User getUser(){
		return User.currentUser();
	}
	@ModelAttribute("stateCodes")
	public StateCode[] getStateCodes(){
		return StateCode.values();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public void myAccount(){
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String myAccount(@ModelAttribute("user") @Valid User user, BindingResult formBinding) {
	    if (formBinding.hasErrors()) {
	        return null;
	    }
	    logger.warn("need to save");
	    return "redirect:/";
	}
}
