package com.rowe.trivia.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.rowe.trivia.domain.User;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	public void signUp(){
		logger.info("Sign up");
	}
	
	//@ModelAttribute("user")
	@RequestMapping(value="/signup.html", method=RequestMethod.GET)
	public User signUp(WebRequest request){
		User user = new User();
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
	    if (connection != null) {
	    	UserProfile userProfile = connection.fetchUserProfile();
	        user.setUsername(userProfile.getUsername());
	    }
		logger.info("Built user {}", user);
		return user;
	}
	
	@RequestMapping(value="/signup.html", method=RequestMethod.POST)
	public String signup(@Valid User user, BindingResult formBinding, WebRequest request) {
	    if (formBinding.hasErrors()) {
	        return null;
	    }
	    if (user != null) {
	        ProviderSignInUtils.handlePostSignUp(user.getUsername(), request);
	        
	        user.save();
	        user.signInAsCurrentUser();
	        return "redirect:/";
	    }
	    return null;
	}
	
	@RequestMapping(value="/myAccount.html", method=RequestMethod.GET)
	public User myAccount(WebRequest request){
		User user = User.currentUser();
		return user;
	}
	
	@RequestMapping(value="/myAccount.html", method=RequestMethod.POST)
	public String myAccount(User user, BindingResult formBinding, WebRequest request) {
	    if (formBinding.hasErrors()) {
	        return null;
	    }
	    user.save();
	    return "redirect:/myAccount.html";
	}
}
