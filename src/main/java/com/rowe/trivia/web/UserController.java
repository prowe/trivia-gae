package com.rowe.trivia.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.rowe.trivia.domain.User;
import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.repo.UserRepository.UsernameTakenExeption;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepo;

	@RequestMapping(value="/signup.html", method=RequestMethod.GET)
	public User signUp(WebRequest request){
		User user = new User();
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
	    if (connection != null) {
	    	UserProfile userProfile = connection.fetchUserProfile();
	        user.setEmail(userProfile.getEmail());
	        user.setDisplayName(userProfile.getName());
	    }
		logger.info("Built user {}", user);
		return user;
	}
	
	@RequestMapping(value="/signup.html", method=RequestMethod.POST)
	public String signup(@Valid User user, BindingResult formBinding, WebRequest request) {
	    if (formBinding.hasErrors()) {
	        return null;
	    }
	    //not the best place for this really
	    try {
	    	userRepo.saveNewUser(user);
	    }catch (UsernameTakenExeption e){
	    	logger.warn("User name already taken {}", user.getUsername());
	    	formBinding.rejectValue("email", null, "Email address already taken");
	    	return null;
	    }
	    //now we know the email isn't taken.
        ProviderSignInUtils.handlePostSignUp(user.getUsername(), request);
        
        user.signUpForInProgressContests();
        userRepo.save(user);
        user.signInAsCurrentUser();
        return "redirect:/";
	}
	
}
