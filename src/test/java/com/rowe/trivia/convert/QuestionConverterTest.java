package com.rowe.trivia.convert;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rowe.trivia.domain.Question;
import com.rowe.trivia.repo.QuestionRepository;

public class QuestionConverterTest {

	private QuestionRepository repo;
	private QuestionConverter converter;

	@Before
	public void setUp() throws Exception {
		repo = EasyMock.createMock(QuestionRepository.class);
		converter = new QuestionConverter(repo);
	}

	@After
	public void tearDown() throws Exception {
		EasyMock.reset(repo);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testConvert_nullRepo() {
		converter = new QuestionConverter(null);
		converter.convert("a");
	}

	@Test
	public void testConvert_null() {
		EasyMock.replay(repo);
		assertNull(converter.convert(" "));
		EasyMock.verify(repo);
	}
	
	@Test
	public void testConvert_blank() {
		EasyMock.replay(repo);
		assertNull(converter.convert(" "));
		EasyMock.verify(repo);
	}

	@Test
	public void testConvert_normal() {
		Question q = new Question();
		EasyMock.expect(repo.getById("a")).andReturn(q);
		EasyMock.replay(repo);
		assertSame(q, converter.convert("a"));
		EasyMock.verify(repo);
	}
}
