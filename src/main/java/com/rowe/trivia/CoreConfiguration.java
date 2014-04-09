package com.rowe.trivia;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.env.Environment;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.TranslatorRegistry;
import com.googlecode.objectify.impl.translate.URLTranslatorFactory;
import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.Question;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.QuestionRepository;
import com.rowe.trivia.repo.UserQuestionRepository;
import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.repo.objectify.JodaTimeTranslatorFactory;
import com.rowe.trivia.repo.objectify.ObjectifyContestRepository;
import com.rowe.trivia.repo.objectify.ObjectifyQuestionRepository;
import com.rowe.trivia.repo.objectify.ObjectifyUserQuestionRepository;
import com.rowe.trivia.repo.objectify.ObjectifyUserRepository;
import com.rowe.trivia.repo.objectify.PeriodTranslatorFactory;
import com.rowe.trivia.strategy.ContestSelectionStrategy;
import com.rowe.trivia.strategy.QuestionSelectionStrategy;
import com.rowe.trivia.strategy.contest.EarliestEndingSelectionStrategy;
import com.rowe.trivia.strategy.question.RandomUnaskedSelectionStrategy;

@Import({
	DevelopmentConfiguration.class, 
	ProductionConfiguration.class,
	SecurityConfiguration.class,
	SocialConfiguration.class
})
@Configuration
@EnableSpringConfigured
public class CoreConfiguration implements InitializingBean{
	@Autowired
	private Environment env;
	
	@Bean
	public QuestionSelectionStrategy questionSelectionStrategy(){
		return new RandomUnaskedSelectionStrategy();
	}
	@Bean
	public ContestSelectionStrategy contestSelectionStrategy(){
		return new EarliestEndingSelectionStrategy();
	}
	
	@Bean
	public DatastoreService datastoreService() {
		return DatastoreServiceFactory.getDatastoreService();
	}

	@Bean
	public UserRepository userRepository() {
		return new ObjectifyUserRepository();
	}
	@Bean
	public ContestRepository contestRepository() {
		return new ObjectifyContestRepository();
	}
	@Bean
	public UserQuestionRepository userQuestionRepository(){
		return new ObjectifyUserQuestionRepository();
	}
	@Bean
	public QuestionRepository questionRepository(){
		return new ObjectifyQuestionRepository();
	}
	
	/*
	@Bean
	public EmailService emailService(){
		EmailServiceImpl service = new EmailServiceImpl(env.getProperty("applicationURL"));
		service.setFromAddress(env.getProperty("email.fromAddress"));
		return service;
	}*/
	
	@Override
	public void afterPropertiesSet() throws Exception {
		registerObjectify();
	}
	
	//TODO: should be a better way of doing this
	public static void registerObjectify(){
		TranslatorRegistry translators = ObjectifyService.factory().getTranslators();
		translators.add(new JodaTimeTranslatorFactory());
		translators.add(new PeriodTranslatorFactory());
		translators.add(new URLTranslatorFactory());
		
		ObjectifyService.register(User.class);
		ObjectifyService.register(Contest.class);
		ObjectifyService.register(UserQuestion.class);
		ObjectifyService.register(Question.class);
	}
}
