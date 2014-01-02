package com.rowe.trivia;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("Production")
@PropertySource("classpath:/com/rowe/trivia/Production.properties")
public class ProductionConfiguration {

}
