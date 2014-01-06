package com.rowe.trivia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import com.google.appengine.api.datastore.DatastoreService;
import com.rowe.trivia.repo.dataStore.DatastoreUsersConnectionRepository;
import com.rowe.trivia.security.SpringSecuritySignInAdapter;

@Configuration
public class SocialConfiguration {
	@Autowired
	private Environment environment;
	
	@Autowired
	private DatastoreService datastoreService;
	
	@Bean
	@Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES)
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(new TwitterConnectionFactory(
			environment.getProperty("twitter.consumerKey"), 
			environment.getProperty("twitter.consumerSecret"))
		);
		registry.addConnectionFactory(new FacebookConnectionFactory(
				environment.getProperty("facebook.appId"), 
				environment.getProperty("facebook.appSecret"))
			);
		return registry;
	}

	/*@Bean
    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
    public ConnectionRepository connectionRepository(){
		User currentUser = User.currentUser();
        if (currentUser == null) {
            throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
        }
        return usersConnectionRepository().createConnectionRepository(currentUser.getUsername());
    }*/
	
	@Bean
    public UsersConnectionRepository usersConnectionRepository() {
        return new DatastoreUsersConnectionRepository(connectionFactoryLocator(), Encryptors.noOpText(), datastoreService);
    }
	
	@Bean
	public SignInAdapter signInAdapter(){
		return new SpringSecuritySignInAdapter();
	}
}
