package com.rowe.trivia.web;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rowe.trivia.domain.NotificationMethod;
import com.rowe.trivia.domain.User;

@Controller
@RequestMapping("/users/myAccount.html")
public class MyAccountController {
	private static final Logger logger = LoggerFactory.getLogger(MyAccountController.class);
	
	@ModelAttribute("user")
	public User getUser(){
		return User.currentUser();
	}
	@ModelAttribute("notificationMethods")
	public List<NotificationMethod> getNotificationMethods(){
		return Arrays.asList(NotificationMethod.values());
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public void myAccount(){
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String myAccount(@ModelAttribute("user") @Valid User user, BindingResult formBinding) {
	    if (formBinding.hasErrors()) {
	        return null;
	    }
	    user.save();
	    return "redirect:/";
	}
}
