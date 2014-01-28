package com.rowe.trivia.convert;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.rowe.trivia.domain.Question;
import com.rowe.trivia.repo.QuestionRepository;

@RunWith(EasyMockRunner.class)
public class QuestionConverterTest {

	@Mock
	private QuestionRepository repo;
	
	@TestSubject
	private QuestionConverter converter = new QuestionConverter(repo);
	
	@Test(expected=IllegalStateException.class)
	public void testConvert_nullRepo() {
		QuestionConverter convert = new QuestionConverter(null);
		convert.convert("a");
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
