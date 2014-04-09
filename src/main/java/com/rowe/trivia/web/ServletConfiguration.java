package com.rowe.trivia.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.rowe.trivia.convert.PeriodFormatter;

@Configuration
//@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
public class ServletConfiguration extends WebMvcConfigurationSupport{
	
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	@Autowired
	private SignInAdapter signInAdapter;
	
	//@Autowired
	//private ConnectionRepository connectionRepository;
	
	//@Autowired
	//private QuestionRepository questionRepo;
	
	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver jsonViewResolver = new InternalResourceViewResolver();
		jsonViewResolver.setPrefix("/WEB-INF/jsp/");
		jsonViewResolver.setSuffix(".json.jsp");
		jsonViewResolver.setContentType(MediaType.APPLICATION_JSON.toString());
		
		InternalResourceViewResolver defaultViewResolver = new InternalResourceViewResolver();
		defaultViewResolver.setPrefix("/WEB-INF/jsp/");
		defaultViewResolver.setSuffix(".jsp");
		
		ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
		viewResolver.setViewResolvers(Arrays.asList((ViewResolver)jsonViewResolver, defaultViewResolver));
		return viewResolver;
	}
	
	@Override
	protected Validator getValidator() {
		LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
		return factoryBean;
	}
	
	@Override
	protected void addFormatters(FormatterRegistry registry) {
		super.addFormatters(registry);
		registry.addFormatter(new PeriodFormatter());
		//registry.addConverter(new QuestionConverter(questionRepo));
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
			.addResourceLocations("/resources/")
			.setCachePeriod(0);
	}
	
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("WEB-INF/messages");
		messageSource.setCacheSeconds(5);
		return messageSource;
	}
	
	@Bean
	public WelcomeController welcomeController(){
		return new WelcomeController();
	}
	@Bean
	public UserController userController(){
		return new UserController();
	}
	@Bean
	public UserQuestionController userQuestionController(){
		return new UserQuestionController();
	}
	@Bean
	public ContestController contestController(){
		return new ContestController();
	}

	@Bean
	public ProviderSignInController providerSignInController() {
		ProviderSignInController controller = new ProviderSignInController(connectionFactoryLocator, usersConnectionRepositoryProxy(), signInAdapter);
		controller.setSignInUrl("/");
		controller.setSignUpUrl("/users/signup.html");
		return controller;
	}
	
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
	public UsersConnectionRepository usersConnectionRepositoryProxy(){
		//If i don't do this here i get a not serializable exception when trying to sign up
		return usersConnectionRepository;
	}
}
