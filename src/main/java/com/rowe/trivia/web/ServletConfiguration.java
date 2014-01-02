package com.rowe.trivia.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.rowe.trivia.SecurityConfiguration;
import com.rowe.trivia.security.SpringSecuritySignInAdapter;

@Configuration
//@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
public class ServletConfiguration extends WebMvcConfigurationSupport{
	
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	//@Autowired
	//private ConnectionRepository connectionRepository;
	@Autowired
	private SignInAdapter signInAdapter;
	
	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
			.addResourceLocations("/resources/")
			.setCachePeriod(0);
	}
	
	@Bean
	public WelcomeController helloController(){
		return new WelcomeController();
	}
	@Bean
	public UserController userController(){
		return new UserController();
	}
	
	@Bean
	public DebugController debugController(){
		return new DebugController();
	}
	@Bean
	public ContestController contestController(){
		return new ContestController();
	}
	@Bean
	public UserQuestionController userQuestionController(){
		return new UserQuestionController();
	}
	
	/*@Bean
    public ConnectController connectController() {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }*/
	
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
