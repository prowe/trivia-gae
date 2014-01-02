package com.rowe.trivia;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("Development")
@PropertySource("classpath:/com/rowe/trivia/Development.properties")
public class DevelopmentConfiguration {

}
