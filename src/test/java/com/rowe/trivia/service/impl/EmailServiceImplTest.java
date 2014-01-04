package com.rowe.trivia.service.impl;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.rowe.trivia.CoreConfiguration;
import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.User;
import com.rowe.trivia.domain.UserQuestion;

public class EmailServiceImplTest {

	private EmailServiceImpl service;
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	@Before
	public void setup(){
		helper.setUp();
		service = new EmailServiceImpl("http://localhost:8080");
		CoreConfiguration.registerObjectify();
	}
	@After
	public void tearDown() { 
		helper.tearDown();
	}
	
	@Test
	public void buildQuestionAskedBody() throws IOException{
		User contestant = new User();
		contestant.setDisplayName("Display Name");
		contestant.setUsername("username");
		Contest contest = new Contest();
		contest.setContestId("1234");
		contest.setQuestion("Question goes here");
		contest.setPossibleAnswers(Arrays.asList("ans 1", "ans 2", "ans 3", "ans 4"));
		contest.setPrizeDescription("Great Prize");
		UserQuestion uq = new UserQuestion(contestant, contest);
		
		assertNotNull(uq.getContest());
		assertNotNull(uq.getContestant());
		String body = service.buildQuestionAskedBody(uq);
		
		File f = new File("target/emails/buildQuestionAskedBody.html");
		f.getParentFile().mkdirs();
		Writer writer = new FileWriter(f);
		IOUtils.write(body, writer);
		IOUtils.closeQuietly(writer);
	}
}
