package com.rowe.trivia;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.UserQuestionRepository;
import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.repo.dataStore.DatastoreContestRepository;
import com.rowe.trivia.repo.dataStore.DatastoreUserQuestionRepository;
import com.rowe.trivia.repo.dataStore.DatastoreUserRepository;
import com.rowe.trivia.repo.dataStore.DatastoreUsersConnectionRepository;
import com.rowe.trivia.repo.jdo.JDOContestRepository;
import com.rowe.trivia.repo.jdo.JDOUserQuestionRepository;
import com.rowe.trivia.repo.jdo.JDOUserRepository;

@Import({
	DevelopmentConfiguration.class, 
	ProductionConfiguration.class,
	SecurityConfiguration.class,
	SocialConfiguration.class
})
//@ImportResource("classpath:/com/rowe/trivia/securityConfiguration.xml")
@Configuration
@EnableSpringConfigured
public class CoreConfiguration {
	@Autowired
	private Environment environment;
	
	@Bean
	@Lazy(true)
	public PersistenceManagerFactory persistenceManagerFactory(){
		return JDOHelper.getPersistenceManagerFactory("transactions-optional");
	}

	@Bean
	public DatastoreService datastoreService() {
		return DatastoreServiceFactory.getDatastoreService();
	}

	@Bean
	public UserRepository userRepository() {
		return new JDOUserRepository(persistenceManagerFactory());
	}
	@Bean
	public ContestRepository contestRepository() {
		return new JDOContestRepository(persistenceManagerFactory(), userRepository());
	}
	@Bean
	public UserQuestionRepository userQuestionRepository(){
		return new JDOUserQuestionRepository(persistenceManagerFactory(), userRepository());
	}
	
}
