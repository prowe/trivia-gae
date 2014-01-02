package com.rowe.trivia.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.AbstractEnvironment;

/**
 * Sets the active profile from com.google.appengine.runtime.environment
 * @author paulrowe
 *
 */
public class ActiveProfileSetter implements ServletContextListener{
	private static Logger logger = LoggerFactory.getLogger(ActiveProfileSetter.class);
	private static final String APP_ENGINE_ENVIRONMENT_PROPERTY = "com.google.appengine.runtime.environment";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String env = System.getProperty(APP_ENGINE_ENVIRONMENT_PROPERTY);
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, env);
		logger.info("Set active profile to {}", env);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
