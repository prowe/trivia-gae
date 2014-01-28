package com.rowe.trivia.domain;

import static org.junit.Assert.*;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.rowe.trivia.ObjectifyTestCaseSupport;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.UserQuestionRepository;
import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.service.EmailService;

@Ignore
@RunWith(EasyMockRunner.class)
public class ContestTest extends ObjectifyTestCaseSupport{
	
	@Mock
	private ContestRepository repo;
	@Mock
	private UserRepository userRepo;
	@Mock
	private UserQuestionRepository userQuestionRepo;
	@Mock
	private EmailService emailService;
	
	@TestSubject
	private Contest contest;
	
	@Before
	public void setup(){
		contest = new Contest();
	}

	@Test
	public void testCompleteCreation() {
		User user = new User("user");
		user.signInAsCurrentUser();
		
		contest.completeCreation();
		assertNotNull(contest.getContestId());
		assertSame(user, contest.getSponsor());
		
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsElgible() {
		User sponsor = new User("s");
		contest.setSponsor(sponsor);
		
		User currentUser = new User("cu");
		currentUser.signInAsCurrentUser();
		
		assertTrue(contest.isElgible(currentUser));
		assertFalse(contest.isElgible(sponsor));
	}

	@Test
	public void testStart() {
		fail("Not yet implemented");
	}

	@Test
	public void testEnd() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPrize() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPrize() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSponsor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContestId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSponsor() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetContestId() {
		contest.setContestId("1234");
		assertEquals("1234", contest.getContestId());
	}

	@Test
	public void testSetQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDuration() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDuration() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPrizeQuantity() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPrizeQuantity() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStartTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEndTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWinningAnswers() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsInProgress() {
		fail("Not yet implemented");
	}

}
